package br.ufjf.nenc.broadecos.api.experience;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Activity extends br.ufjf.nenc.broadecos.api.experience.Object {

    public static ActivityBuilder builder() {
        return new ActivityBuilder();
    }

    @Override
    public String getObjectType(){
        return "Activity";

    }

    @Override
    public void setObjectType(String objectType) {
        throw new UnsupportedOperationException();
    }

    public static class ActivityBuilder {
        ActivityBuilder() {
        }

        public Activity build() {
            return new Activity();
        }

        public String toString() {
            return "br.ufjf.nenc.broadecos.api.experience.Activity.ActivityBuilder()";
        }
    }
}
