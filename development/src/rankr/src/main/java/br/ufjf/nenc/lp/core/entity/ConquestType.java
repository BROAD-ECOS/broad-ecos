package br.ufjf.nenc.lp.core.entity;

public enum ConquestType {
    POINTS {
        @Override
        public boolean isGreater(String one, String another) {
            Long oneLong = Long.valueOf(one);
            Long anotherLong = Long.valueOf(another);
            return  oneLong.compareTo(anotherLong) > 0;
        }

        public ConquestSummary newSummary() {
            return new PointsSummary();
        }
    } ;

    public static ConquestType from(String conquestType) {
        return valueOf(conquestType.toUpperCase());
    }

    public abstract boolean isGreater(String one, String another);

    public abstract ConquestSummary newSummary();
}
