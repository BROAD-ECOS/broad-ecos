package br.ufjf.nenc.lp.core.bo;

import br.ufjf.nenc.lp.core.entity.ConquestType;
import br.ufjf.nenc.lp.core.pl.ir.Param;

public class AcquiredConquest {

    private final String participantBizId;

    private final String participantName;

    private final String learningObjectId;

    private final String interaction;

    private final String conquestType;

    private final String value;


    private AcquiredConquest(String participantBizId, String participantName, String learningObjectId, String interaction, String conquestType, String value) {
        this.participantBizId = participantBizId;
        this.participantName = participantName;
        this.learningObjectId = learningObjectId;
        this.conquestType = conquestType;
        this.value = value;
        this.interaction = interaction;
    }

    public String getParticipantBizId() {
        return participantBizId;
    }

    public String getLearningObjectId() {
        return learningObjectId;
    }

    public ConquestType getConquestType() {
        return ConquestType.from(conquestType);
    }

    public String getValue() {
        return value;
    }

    public String getInteraction() {
        return interaction;
    }

    public String getParticipantName() {
        return participantName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AcquiredConquest{");
        sb.append("participantBizId='").append(participantBizId).append('\'');
        sb.append(", participantName='").append(participantName).append('\'');
        sb.append(", learningObjectId='").append(learningObjectId).append('\'');
        sb.append(", interaction='").append(interaction).append('\'');
        sb.append(", conquestType='").append(conquestType).append('\'');
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static class Builder {
        private String participantBizId;
        private String learningObjectId;
        private String conquestType;
        private String value;
        private String interaction;
        private String participantName;

        public Builder() {}

        public Builder setConquestType(String conquestType) {
            this.conquestType = conquestType;
            return this;
        }

        public Builder setLearningObjectId(String learningObjectId) {
            this.learningObjectId = learningObjectId;
            return this;
        }

        public Builder setParticipantBizId(String participantBizId) {
            this.participantBizId = participantBizId;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder  setInteraction(String interaction) {
            this.interaction = interaction;
            return this;
        }

        public Builder setParticipantName(String participantName) {
            this.participantName = participantName;
            return this;
        }

        public AcquiredConquest build() {
            return new AcquiredConquest(participantBizId, participantName, learningObjectId, interaction, conquestType, value);
        }
    }
}
