package br.ufjf.nenc.lp.core.scorm;

import br.ufjf.nenc.lp.core.pl.*;
import br.ufjf.nenc.lp.core.template.TemplateEngine;
import br.ufjf.nenc.lp.featuremodel.lo.LearningObjectFeature;
import br.ufjf.nenc.lp.featuremodel.lo.LearningObjectType;
import br.ufjf.nenc.lp.featuremodel.lo.tutorial.TutorialFeature;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

import static br.ufjf.nenc.lp.core.util.CastUtil.cast;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

@Component
public class ScormBuilder implements Builder {

    private static final Logger logger = (Logger) LoggerFactory.getLogger(ScormBuilder.class);

    private static final String SCORM_FORMAT = "SCORM2004";

    @Value("${lo.build.directory}")
    private String buildDirectoryPath;

    @Autowired
    private TemplateEngine engine;


    private File buildDirectory;

    @Override
    public boolean isResponsibleFor(Feature feature) {
        return feature instanceof Packageable && ((Packageable) feature).getPackageFormat().getFormat().equals(SCORM_FORMAT);
    }

    @Override
    public Product build(Feature feature) throws ProductLineException {
        checkNotNull(feature);

        Scorm scorm = null;

        logger.info(String.format("Build feature: %s", feature));

        LearningObjectFeature lo = cast(feature, LearningObjectFeature.class);

        if (hasType(lo, TutorialFeature.class)) {
            scorm = new ScormTutorialAssembler(lo, getBuildDirectory(), engine).assemble();
        }

        return scorm;
    }

    private boolean hasType(LearningObjectFeature lo, Class<? extends LearningObjectType> clazz) {
        return lo.getType().getType().getClass().isAssignableFrom(clazz);
    }

    private File getBuildDirectory() {
        if (buildDirectory == null) {
            buildDirectory = createBuildDirectoryFile();
        }

        return buildDirectory;
    }

    private File createBuildDirectoryFile() {

        File buildDirectory = new File(buildDirectoryPath);
        buildDirectory.mkdirs();

        checkState(buildDirectory.exists());
        checkState(buildDirectory.canWrite());
        checkState(buildDirectory.isDirectory());

        return buildDirectory;
    }
}
