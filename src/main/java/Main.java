import org.jdom2.JDOMException;
import sections.Assessor;
import utilities.Logger;
import utilities.SectionName;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException, JDOMException {
        String message = """
                ╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗
                ║                                                                                                                        ║
                ║                                                                                                                        ║
                ║ mmmmmmm #                   mmmmmmm                      #             mmmm                                            ║
                ║    #    # mm    mmm            #     mmm    m mm   mmm   # mm         #"   "  mmm    m mm   mmm   mmmm    mmm    m mm  ║
                ║    #    #"  #  #"  #           #    #" "#   #"  " #"  "  #"  #        "#mmm  #"  "   #"  " "   #  #" "#  #"  #   #"  " ║
                ║    #    #   #  #""\""           #    #   #   #     #      #   #            "# #       #     m""\"#  #   #  #""\""   #     ║
                ║    #    #   #  "#mm"           #    "#m#"   #     "#mm"  #   #        "mmm#" "#mm"   #     "mm"#  ##m#"  "#mm"   #     ║
                ║                                                                                                   #                    ║
                ║                                                                                                   "                    ║
                ║                                                                                                                        ║
                ║════════════════════════════╦══════════════════════════════════════════════════════════════╦════════════════════════════║
                ║  Created By                ║  Analyzing student journalists of Wichita South High School  ║             Version 1.2.3  ║
                ║  The Vacuous Versifier     ║                          Since 2022                          ║         The Basic Edition  ║
                ╚════════════════════════════╩══════════════════════════════════════════════════════════════╩════════════════════════════╝""";

        Logger.log(SectionName.SILENT, message);

        Logger.log(SectionName.PROGRAM, "Welcome to the Torch Scraper.");

        Assessor assessor = new Assessor();
        assessor.run();

        Logger.log(SectionName.PROGRAM, "Finish");
    }
}
