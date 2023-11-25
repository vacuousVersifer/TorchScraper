import java.io.IOException;
import java.net.URISyntaxException;

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


        Assessor assessor = new Assessor();
        assessor.run();

        Logger.log(SectionName.PROGRAM, "Finish");
    }
}
