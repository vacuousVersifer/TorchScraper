import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        String message =
                """
                        ╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗
                        ║                                                                                                                        ║
                        ║ mmmmmmm #                   mmmmmmm                      #             mmmm                                            ║
                        ║    #    # mm    mmm            #     mmm    m mm   mmm   # mm         #"   "  mmm    m mm   mmm   mmmm    mmm    m mm  ║
                        ║    #    #"  #  #"  #           #    #" "#   #"  " #"  "  #"  #        "#mmm  #"  "   #"  " "   #  #" "#  #"  #   #"  " ║
                        ║    #    #   #  #""\""           #    #   #   #     #      #   #            "# #       #     m""\"#  #   #  #""\""   #     ║
                        ║    #    #   #  "#mm"           #    "#m#"   #     "#mm"  #   #        "mmm#" "#mm"   #     "mm"#  ##m#"  "#mm"   #     ║
                        ║                                                                                                                        ║
                        ╚════════════════════════════════════════════════╗      Created By      ╔════════════════════════════════════════════════╝
                                                                         ║ The VacuousVersifier ║                                                \s
                                                                         ╚══════════════════════╝                                                \s
                        """;
        Logger.log(SectionName.SILENT, message);

        Logger.log(SectionName.PROGRAM, "Begin");

        if (askDev()) {
            // Do dev stuff
        } else {
            Assessor assessor = new Assessor();
            assessor.run();
        }

        Logger.log(SectionName.PROGRAM, "Finish");
    }

    private static boolean askDev() {
        Logger.log(SectionName.CONSTRUCTOR, "Use dev mode? (Y/N): ", false);
        String devResponse = new Scanner(System.in).nextLine();
        if (devResponse.equals("Y")) {
            return true;
        } else if (devResponse.equals("N")) {
            return false;
        } else {
            Logger.log(SectionName.PROGRAM, "Invalid response. Defaulting to no");
            return false;
        }
    }
}
