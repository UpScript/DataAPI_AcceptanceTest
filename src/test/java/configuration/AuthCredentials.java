package configuration;

import java.util.Map;

public class AuthCredentials {
    public static final Map<String, Map<String, String>> CREDENTIALS = Map.of(
            "QA", Map.of(
                    "PFIZER_USERNAME", "Pfizer54173",
                    "PFIZER_PASSWORD", "Pfizer54173",
                    "LOMBARD_USERNAME", "Lombard278",
                    "LOMBARD_PASSWORD", "xjpFAXGdznvkA"
            ),
            "UAT", Map.of(
                    "PFIZER_USERNAME", "PfizerUAT",
                    "PFIZER_PASSWORD", "PfizerUATPassword",
                    "LOMBARD_USERNAME", "LombardUAT",
                    "LOMBARD_PASSWORD", "LombardUATPassword"
            ),
            "PROD", Map.of(
                    "PFIZER_USERNAME", "PfizerProd",
                    "PFIZER_PASSWORD", "PfizerProdPassword",
                    "LOMBARD_USERNAME", "LombardProd",
                    "LOMBARD_PASSWORD", "LombardProdPassword"
            )
    );
}

//public class TestConstant {
//
//    public static final String PFIZER_AUTH_USERNAME = "Pfizer54173";
//    public static final String PFIZER_AUTH_PASSWORD = "Pfizer54173";
//
//    public static final String LOMBARD_AUTH_USERNAME = "Lombard278";
//    public static final String LOMBARD_AUTH_PASSWORD = "xjpFAXGdznvkA";
//
//    public static final String BOSLEY_AUTH_USERNAME = "Bosley17956";
//    public static final String BOSLEY_AUTH_PASSWORD = "yz1wmQCNFdHbR";
//
//    public static final String NERIVIO_AUTH_USERNAME = "Nerivio86709";
//    public static final String NERIVIO_AUTH_PASSWORD = "YzdmYTAwZTQ0Y3#";
//}
