package br.ufjf.nenc.lp.core.entity;

import com.google.common.collect.ImmutableMap;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.*;

public class Profile implements Serializable {

    @Id
    private String id;

    private Participant participant;

    private Long score;

    private Date createdAt;

    private Date updatedAt;

    private Map<ConquestType, ConquestSummary> conquests;

    public void summarize(Conquest conquest) {

        conquests = Optional.ofNullable(conquests).orElseGet(()->new HashMap<>());

        Optional<ConquestSummary> summaryNullable = Optional.ofNullable(conquests.get(conquest.getType()));

        ConquestSummary summary = summaryNullable.orElseGet(()->conquest.getType().newSummary());

        summary.summarize(conquest);

        conquests.put(conquest.getType(), summary);

        updatedAt = new Date();

        updateScore();
    }

    public void updateSummary(Conquest conquest, String newValue) {

        conquests = Optional.ofNullable(conquests).orElseGet(()->new HashMap<>());

        Optional<ConquestSummary> summaryNullable = Optional.ofNullable(conquests.get(conquest.getType()));

        ConquestSummary summary = summaryNullable.orElseGet(()->conquest.getType().newSummary());

        summary.updateSummary(conquest, newValue);

        conquests.put(conquest.getType(), summary);

        updatedAt = new Date();

        updateScore();
    }

    private void updateScore() {
        if (conquests != null) {
            long newScore = 0;
            for (ConquestSummary summary : conquests.values()) {
                newScore += summary.getScore();
            }
            score = newScore;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        if (participant != null) {
            participant.setPasswd(null);
        }
        this.participant = participant;
    }

    public Map<ConquestType, ConquestSummary> getConquests() {
        return ImmutableMap.copyOf(conquests);
    }

    public void setConquests(Map<ConquestType, ConquestSummary> conquests) {
        this.conquests = conquests;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Profile{");
        sb.append("id='").append(id).append('\'');
        sb.append(", participant=").append(participant);
        sb.append(", score=").append(score);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", conquests=").append(conquests);
        sb.append('}');
        return sb.toString();
    }
}
