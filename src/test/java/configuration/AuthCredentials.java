package configuration;

import java.util.Map;

public class AuthCredentials {
    public static final Map<String, Map<String, String>> CREDENTIALS = Map.of(
            "QA", Map.of(
                    "PFIZER_USERNAME", "Pfizer54173",
                    "PFIZER_PASSWORD", "Pfizer54173",
                    "LOMBARD_USERNAME", "Lombard278",
                    "LOMBARD_PASSWORD", "xjpFAXGdznvkA",
                    "BOSLEY_USERNAME", "Bosley17956",
                    "BOSLEY_PASSWORD", "yz1wmQCNFdHbR",
                    "NERIVIO_USERNAME", "Nerivio86709",
                    "NERIVIO_PASSWORD", "YzdmYTAwZTQ0Y3#"
            ),
            "UAT", Map.of(
                    "PFIZER_USERNAME", "Pfizer54173",
                    "PFIZER_PASSWORD", "2xPxsaQRD3qXG",
                    "LOMBARD_USERNAME", "Lombard278",
                    "LOMBARD_PASSWORD", "xjpFAXGdznvkA",
                    "BOSLEY_USERNAME", "Bosley17956",
                    "BOSLEY_PASSWORD", "yz1wmQCNFdHbR",
                    "NERIVIO_USERNAME", "Nerivio6574",
                    "NERIVIO_PASSWORD", "2mhnUDBMr2mFv"
            )
    );
}
