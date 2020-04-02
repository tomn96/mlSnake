package run;

import game.SmartSnake;
import game.WindowGame;

public class ModelRunner {

    public static void main(String[] args) {
//        String[] paths = {"train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:29:25.084257_1_5", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:29:27.106186_2_5", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:29:29.109324_3_5", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:29:31.181822_4_6", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:29:34.292594_5_14", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:29:40.769409_6_27", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:29:49.498943_7_40", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:29:58.874443_8_40", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:30:07.789817_9_40", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:30:17.706574_10_43", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:30:36.514078_11_62", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:30:52.496718_12_62", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:31:18.460008_13_62", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:31:51.042920_14_73", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:32:24.127404_15_73", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:32:43.482964_16_73", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:33:02.321170_17_73", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:33:23.952221_18_73", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:33:51.347721_19_73", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:34:15.443949_20_73", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:34:41.599428_21_73", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:35:10.221802_22_73", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:35:38.637350_23_73", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:36:02.263511_24_74", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:36:33.011683_25_77", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:36:54.156291_26_87", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:37:37.822436_27_87", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:38:30.402293_28_89", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:39:11.618119_29_89", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:40:02.363023_30_89", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:40:44.468466_31_91", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:41:16.998171_32_91", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:41:49.139217_33_91", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:42:17.461866_34_91", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:42:31.532195_35_91", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:43:01.917006_36_92", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:43:26.564730_37_92", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:44:06.110120_38_92", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:44:40.805816_39_94", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:45:17.687662_40_94", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:45:54.464387_41_94", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:46:19.775631_42_98", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:46:39.325749_43_98", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:47:01.862626_44_98", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:47:23.207194_45_98", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:47:48.924677_46_98", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:48:12.928042_47_98", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:48:42.375753_48_98", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:49:19.222363_49_98", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:49:47.889933_50_98", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:50:18.094182_51_98", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:50:39.238068_52_98", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:50:53.242846_53_98", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:51:18.870560_54_107", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:51:44.111213_55_110", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:52:00.966912_56_110", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:52:18.397514_57_110", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:52:39.316178_58_110", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:52:53.151726_59_110", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:53:11.279552_60_110", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:53:37.017020_61_110", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:53:55.953648_62_110", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:54:25.316274_63_110", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:54:49.093485_64_110", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:55:18.842024_65_110", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:55:52.092229_66_112", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:56:24.914473_67_112", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:56:58.987057_68_112", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:57:36.740969_69_112", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:57:57.152089_70_112", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:58:17.717220_71_112", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:58:35.084283_72_112", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:58:54.145724_73_112", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:59:20.646275_74_112", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T03:59:55.647455_75_112", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:00:22.842834_76_112", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:00:43.235483_77_112", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:01:03.088926_78_112", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:01:38.763842_79_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:02:02.879852_80_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:02:26.864366_81_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:02:52.886038_82_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:03:19.425084_83_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:03:39.386250_84_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:04:14.048408_85_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:04:44.449125_86_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:05:09.514516_87_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:05:30.108471_88_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:05:53.346077_89_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:06:17.640533_90_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:06:54.913603_91_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:07:23.644577_92_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:08:02.656966_93_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:08:28.873328_94_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:09:08.861474_95_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:09:37.734831_96_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:10:16.025492_97_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:10:43.365900_98_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:11:12.107414_99_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:11:48.888408_100_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:12:14.732944_101_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:12:50.185929_102_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:13:25.071971_103_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:14:12.740047_104_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:15:01.707952_105_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:15:47.779606_106_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:16:23.217365_107_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:16:56.887749_108_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:17:37.324042_109_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:18:15.974042_110_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:18:50.385675_111_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:19:30.140267_112_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:20:53.174597_113_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:22:03.228096_114_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:23:01.806419_115_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:23:46.548396_116_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:24:27.929255_117_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:25:19.341060_118_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:26:21.206034_119_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:27:38.480459_120_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:28:52.061051_121_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:29:28.908604_122_113", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:30:28.148727_123_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:31:06.683143_124_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:31:58.368242_125_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:32:44.762065_126_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:33:27.078951_127_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:34:28.161719_128_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:35:44.954802_129_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:36:36.687915_130_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:37:47.413090_131_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:38:33.954626_132_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:39:56.122978_133_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:40:53.153069_134_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:41:48.601758_135_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:42:33.151997_136_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:44:21.949825_137_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:45:41.691118_138_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:46:46.064652_139_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:47:30.351555_140_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:47:57.850648_141_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:48:58.371464_142_117", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:49:32.335240_143_121", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:50:06.273663_144_121", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:50:59.294703_145_121", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:51:41.227469_146_121", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:52:58.987186_147_121", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:54:49.494995_148_121", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:56:20.663242_149_122", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:57:59.488951_150_128", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T04:58:58.747556_151_128", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:00:15.702987_152_128", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:01:43.465689_153_128", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:03:15.484839_154_128", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:04:59.929696_155_128", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:06:35.231519_156_128", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:08:11.494645_157_132", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:09:41.606677_158_134", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:10:44.851245_159_134", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:12:06.387663_160_134", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:13:20.992513_161_134", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:14:41.063037_162_134", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:15:51.282847_163_134", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:17:19.933104_164_134", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:18:55.116774_165_146", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:20:18.458643_166_146", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:21:24.773129_167_146", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:23:23.329117_168_147", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:24:54.227990_169_148", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:26:12.177376_170_149", "train_1/2020-04-02T03:29:23.079367_normal/2020-04-02T05:27:16.536336_171_149"};
        String[] paths = {""};
        for (String path : paths) {
            String[] s = path.split("_");
            if (s.length >= 2) {
                WindowGame.generation = Integer.parseInt(s[s.length - 2]);
            }

            SmartSnake snake = (SmartSnake) Saver.loadSnake(path);
            SmartSnake replay = snake.copy();
            WindowGame game = new WindowGame(replay);
            game.join();
        }
    }
}
