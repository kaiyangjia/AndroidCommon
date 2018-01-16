package com.jiakaiyang.androidcommon.androidcommon.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * Created by jia on 2018/1/15.
 * 自动生成适配的布局文件
 * <p>
 * 机顶盒切图放置位置和手机不同
 * UI 按照给手机出切图的方式导出的 xxhpi、xhdpi、hdpi、mdpi等切图，不能直接放置在对应 dpi 的 drawable 目录下。
 * 比如，UI 以 1080 * 1920 为基准，导出的一比一的切图，在手机上，对应的是 xxhdpi，因放置到手机的 drawable-xxhdpi 文件夹中。而在机顶盒上（假设机顶盒分辨率为 1280 * 720 160dpi 或 1920 * 1080 240dpi），对应的应该是 hdpi，因放置到 drawable-hdpi 文件夹中，机顶盒需要其他切图，应该以 hdpi 为基准进行放大或缩小出图。
 * <p>
 * 总结
 * 为了适配大部分机顶盒，UI 应该按照 1920 * 1080 出图，1920 * 1080 效果图导出的1比1的切图，对应的是 hdpi (而非手机开发时候的 xxhdpi)。给 drawable-xhdpi、drawable-hdpi、drawable-mdpi 的切图。编码时，在 value-w1280p、 value-w960p 下分别设不同的值(value-w960p 下的值可按 value-w1280p 中的值等比例缩放获得)。
 * <p>
 * 链接：https://www.jianshu.com/p/f406740263e7
 */

public class GenerateValueFiles {
    private int baseW;
    private int baseH;

    private String dirStr = "common-lib/res";

    private final static String WTemplate = "<dimen name=\"x{0}\">{1}px</dimen>\n";
    private final static String HTemplate = "<dimen name=\"y{0}\">{1}px</dimen>\n";

    /**
     * {0}-HEIGHT
     */
    private final static String VALUE_TEMPLATE = "values-{0}x{1}";
    //youku 1920*1032 mdpi //海信1920*1080 //小米二代 1280*720 mdpi
    /**
     * <p>
     * drawable	机顶盒
     * drawable-mdpi	i71（160dpi）、Letv New C1S(160dpi)、XMATE_A88(迪优美特)（160dpi）、开博尔C9(KBE_H8)（160dpi）、英菲克i9（160dpi）
     * drawable-mdpi-1920x1032	youku（160dpi）
     * drawable-213dpi	小米二代（213dpi）
     * drawable-hdpi	i71s（240dpi）、海信电视（240dpi）
     * drawable-xhdpi	MiBox3（320dpi）、MiBox3_Pro（320dpi）、华为M321（320dpi）
     * <p>
     * value	机顶盒
     * value-w1920dp	youku（160dpi）
     * value-w1280dp	i71s（240dpi）、海信电视（240dpi）、i71（160dpi）、Letv New C1S(160dpi)、XMATE_A88(迪优美特)（160dpi）、开博尔C9(KBE_H8)（160dpi）、英菲克i9（160dpi）
     * value-w961dp	小米二代（213dpi）
     * value-w960dp	MiBox3（320dpi）、MiBox3_Pro（320dpi）、华为M321（320dpi）
     * <p>
     */
    private static final String SUPPORT_DIMESION = "480,320;800,480;854,480;960,540;1024,600;1184,720;1196,720;1280,720;1024,768;1280,800;1280,720;1280,672;1812,1080;1920,1080;1920,1032;2560,1440;";

    private String supportStr = SUPPORT_DIMESION;

    public GenerateValueFiles(int baseX, int baseY, String supportStr) {
        this.baseW = baseX;
        this.baseH = baseY;

        if (!this.supportStr.contains(baseX + "," + baseY)) {
            this.supportStr += baseX + "," + baseY + ";";
        }

        this.supportStr += validateInput(supportStr);

        System.out.println(supportStr);

        File dir = new File(dirStr);
        if (!dir.exists()) {
            dir.mkdir();

        }
        System.out.println(dir.getAbsoluteFile());

    }

    /**
     * @param supportStr w,h_...w,h;
     * @return
     */
    private String validateInput(String supportStr) {
        StringBuffer sb = new StringBuffer();
        String[] vals = supportStr.split("_");
        int w = -1;
        int h = -1;
        String[] wh;
        for (String val : vals) {
            try {
                if (val == null || val.trim().length() == 0)
                    continue;

                wh = val.split(",");
                w = Integer.parseInt(wh[0]);
                h = Integer.parseInt(wh[1]);
            } catch (Exception e) {
                System.out.println("skip invalidate params : w,h = " + val);
                continue;
            }
            sb.append(w + "," + h + ";");
        }

        return sb.toString();
    }

    public void generate() {
        String[] vals = supportStr.split(";");
        for (String val : vals) {
            String[] wh = val.split(",");
            generateXmlFile(Integer.parseInt(wh[0]), Integer.parseInt(wh[1]));
        }

    }

    private void generateXmlFile(int w, int h) {

        StringBuffer sbForWidth = new StringBuffer();
        sbForWidth.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sbForWidth.append("<resources>");
        float cellw = w * 1.0f / baseW;

        System.out.println("width : " + w + "," + baseW + "," + cellw);
        for (int i = 1; i < baseW; i++) {
            sbForWidth.append(WTemplate.replace("{0}", i + "").replace("{1}",
                    change(cellw * i) + ""));
        }
        sbForWidth.append(WTemplate.replace("{0}", baseW + "").replace("{1}",
                w + ""));
        sbForWidth.append("</resources>");

        StringBuffer sbForHeight = new StringBuffer();
        sbForHeight.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sbForHeight.append("<resources>");
        float cellh = h * 1.0f / baseH;
        System.out.println("height : " + h + "," + baseH + "," + cellh);
        for (int i = 1; i < baseH; i++) {
            sbForHeight.append(HTemplate.replace("{0}", i + "").replace("{1}",
                    change(cellh * i) + ""));
        }
        sbForHeight.append(HTemplate.replace("{0}", baseH + "").replace("{1}",
                h + ""));
        sbForHeight.append("</resources>");

        File fileDir = new File(dirStr + File.separator
                + VALUE_TEMPLATE.replace("{0}", h + "")//
                .replace("{1}", w + ""));
        fileDir.mkdir();

        File layxFile = new File(fileDir.getAbsolutePath(), "lay_x.xml");
        File layyFile = new File(fileDir.getAbsolutePath(), "lay_y.xml");
        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(layxFile));
            pw.print(sbForWidth.toString());
            pw.close();
            pw = new PrintWriter(new FileOutputStream(layyFile));
            pw.print(sbForHeight.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static float change(float a) {
        int temp = (int) (a * 100);
        return temp / 100f;
    }

    public static void main(String[] args) {
        int baseW = 320;
        int baseH = 400;
        String addition = "";
        try {
            if (args.length >= 3) {
                baseW = Integer.parseInt(args[0]);
                baseH = Integer.parseInt(args[1]);
                addition = args[2];
            } else if (args.length >= 2) {
                baseW = Integer.parseInt(args[0]);
                baseH = Integer.parseInt(args[1]);
            } else if (args.length >= 1) {
                addition = args[0];
            }
        } catch (NumberFormatException e) {

            System.err
                    .println("right input params : java -jar xxx.jar width height w,h_w,h_..._w,h;");
            e.printStackTrace();
            System.exit(-1);
        }

        new GenerateValueFiles(baseW, baseH, addition).generate();
    }
}
