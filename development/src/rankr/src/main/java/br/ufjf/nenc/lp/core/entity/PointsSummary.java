package br.ufjf.nenc.lp.core.entity;

public class PointsSummary extends ConquestSummary {

    public static final String INCOMPATIBLE_TYPE_ERR_MESSAGE = "Não é possível summarizar conquistas de outros tipos!";
    private long total = 0;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public void summarize(Conquest conquest) {
        if (conquest.getType() == ConquestType.POINTS) {
            long conquestValue = Long.parseLong(conquest.getValue());
            total += conquestValue;
        } else {
            throw new DomainException(INCOMPATIBLE_TYPE_ERR_MESSAGE);
        }
    }

    @Override
    public void updateSummary(Conquest conquest, String newValue) {
        if (conquest.getType() == ConquestType.POINTS) {
            long oldConquestValue = Long.parseLong(conquest.getValue());
            long newConquestValue = Long.parseLong(newValue);
            total -= oldConquestValue;
            total += newConquestValue;
        } else {
            throw new DomainException(INCOMPATIBLE_TYPE_ERR_MESSAGE);
        }
    }

    @Override
    public long getScore() {
        return total;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PointsSummary{");
        sb.append("total=").append(total);
        sb.append('}');
        return sb.toString();
    }

}
