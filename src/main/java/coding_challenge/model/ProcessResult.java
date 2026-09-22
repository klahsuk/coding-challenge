package coding_challenge.model;

public record ProcessResult(
        String rawLine,
        transactionStatus status,
        RiskLevel riskProfile,
        String reason
) {

    public static ProcessResult success(String rawLine, RiskLevel riskProfile) {
        return new ProcessResult(rawLine, transactionStatus.PASS, riskProfile, "");
    }

    public static ProcessResult failure(String rawLine, String reason) {
        return new ProcessResult(rawLine, transactionStatus.FAIL, null, reason);
    }
}

