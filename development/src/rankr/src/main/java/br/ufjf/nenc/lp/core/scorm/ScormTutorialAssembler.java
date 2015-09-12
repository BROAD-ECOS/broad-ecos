package br.ufjf.nenc.lp.core.scorm;

import br.ufjf.nenc.lp.core.template.TemplateEngine;
import br.ufjf.nenc.lp.featuremodel.lo.ConquestFeature;
import br.ufjf.nenc.lp.featuremodel.lo.LearningObjectFeature;
import br.ufjf.nenc.lp.featuremodel.lo.MetadataFeature;
import br.ufjf.nenc.lp.featuremodel.lo.tutorial.StepFeature;
import br.ufjf.nenc.lp.featuremodel.lo.tutorial.TutorialFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.*;

import static br.ufjf.nenc.lp.core.util.CastUtil.cast;

public class ScormTutorialAssembler {

    private static final String TEMPLATE_PATH = "/templates/scorm/";

    private static final String IMS_MANIFEST_TEMPLATE = "imsmanifest.xml";

    private static final String JSON_DATA_FILE =  "lo-data.json";
    private static final String ZIP_SUFFIX = ".zip";

    private final List<String> STATIC_FILES = ImmutableList.of(
            "SCORM_API_wrapper.js",
            "index.html",
            "css/prettify.css",
            "css/bootstrap.min.css",
            "css/jquery.modal.css",
            "img/badge-icon.png",
            "img/gold.png",
            "img/silver.png",
            "img/bronze.png",
            "img/badge.png",
            "img/linegraph.png",
            "img/key.png",
            "img/gaming.png",
            "img/feather.png",
            "img/crosshair.png",
            "img/shield.png",
            "img/goblet.png",
            "js/bootstrap.min.js",
            "js/circular-progress.min.js",
            "js/jquery-latest.js",
            "js/jquery.bootstrap.wizard.min.js",
            "js/jquery.modal.min.js",
            "js/lopl.js",
            "js/mustache.js",
            "js/prettify.js"
     );

    private static final List<String> GENERATED_FILES = ImmutableList.of(
            IMS_MANIFEST_TEMPLATE,
            JSON_DATA_FILE
    );

    private static enum CONTENT_TYPE{
        PLAIN_TEXT("plain-text"),
        RICH_TEXT("rich-text"),
        VIDEO("video"),
        IMAGE("image");

        private final String contentType;

        CONTENT_TYPE(String contentType){this.contentType = contentType;}

        public static String getContentTypeFor(String type) {
            String contentTypeText = null;
            for (CONTENT_TYPE contentType : values()) {
                if (contentType.contentType.equals(type)) {
                    contentTypeText = contentType.name();
                }
            }
            return contentTypeText;
        }
    };

    private static enum CONQUEST_TYPE{
        NONE(""),
        POINTS("points"),
        MEDALS("medals");

        private final String conquestType;

        CONQUEST_TYPE(String conquestType){this.conquestType = conquestType;}

        public static String getConquestTypeFor(String type) {
            String conquestTypeText = NONE.name();
            for (CONQUEST_TYPE conquestType : values()) {
                if (conquestType.conquestType.equals(type)) {
                    conquestTypeText = conquestType.name();
                }
            }
            return conquestTypeText;
        }
    };

    private Optional<ConquestFeature> conquestFeature;

    private final File buildDirectory;

    private final TemplateEngine templateEngine;

    private final IMSManifestScope scope;

    private final TutorialData tutorialData;

    ScormTutorialAssembler(LearningObjectFeature lo, File buildDirectory, TemplateEngine engine) {
        this.buildDirectory = buildDirectory;
        this.templateEngine = engine;
        this.scope = newIMSManifestScope();
        this.tutorialData = new TutorialData(lo.getId());

        TutorialFeature tutorial = cast(lo.getType().getType(), TutorialFeature.class);
        setConquestFeatureIfExists(lo.getConquest());
        setSteps(tutorial.getSteps());
        setMetadata(lo.getMetadata());
    }

    public Scorm assemble() {
        Scorm scorm;

        try {
            scorm = doAssemble();
        } catch (Exception e) {
            throw new ScormBuildException("Failure assembling Scorm package", e);
        }

        return scorm;
    }

    public Scorm doAssemble() throws Exception {

        File directory = createOutputDirectory();
        File assembledFile = new File(directory.getAbsolutePath()+ZIP_SUFFIX);

        copyBaseFiles(directory);

        generateIMSManifestFileForScope(directory, scope);

        generateLODataTo(directory);

        ZipUtil.pack(directory, assembledFile);

        return new Scorm(assembledFile);
    }


    private void generateLODataTo(File directory) throws Exception{

        File file = new File(getDestinationPath(directory, JSON_DATA_FILE));

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(file, tutorialData);

    }

    private IMSManifestScope newIMSManifestScope() {

        IMSManifestScope scope = new IMSManifestScope();

        for (String resourceFile : STATIC_FILES) {
            scope.addResource(new Resource(resourceFile));
        }

        for (String resourceFile : GENERATED_FILES) {
            scope.addResource(new Resource(resourceFile));
        }

        return scope;
    }

    private void generateIMSManifestFileForScope(File directory, IMSManifestScope scope) {
        try {
            InputStream resourcePath = getResourceInputStream(IMS_MANIFEST_TEMPLATE);
            String destinationPath = getDestinationPath(directory, IMS_MANIFEST_TEMPLATE);

            templateEngine.compile(new InputStreamReader(resourcePath), destinationPath, scope);
        } catch (Exception e) {
            throw new ScormBuildException("Failure compiling IMSManifest file",e);
        }
    }

    private void copyBaseFiles(File directory) throws IOException {

        for (String file : STATIC_FILES) {
            InputStream from = getResourceInputStream(file);
            File to = new File(getDestinationPath(directory, file));
            to.getParentFile().mkdirs();
            Files.copy(from, to.toPath());
        }
    }

    private String getDestinationPath(File directory, String file) {
        return directory.getAbsolutePath()+ File.separator+file;
    }

    private InputStream getResourceInputStream(String file) {
        return this.getClass().getResourceAsStream(TEMPLATE_PATH + file);
    }

    private File createOutputDirectory() {
        File dir = new File(buildDirectory.getPath()+File.separator+generateOutputDirectoryName());
        if (!dir.mkdirs()) {
            throw new RuntimeException("Failure creating directory to assmebly scorm package.");
        }
        return dir;
    }

    private String generateOutputDirectoryName() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    public void setMetadata(MetadataFeature metadata) {
        scope.setTitle(metadata.getLom().getTitle());
        scope.setDescription(metadata.getLom().getDescription());
        scope.setOrganization(new Organization(metadata.getLom().getOrganizationId(), metadata.getLom().getOrganizationName()));

        tutorialData.setTitle(metadata.getLom().getTitle());
        tutorialData.setDescription(metadata.getLom().getDescription());
    }

    public void setSteps(Collection<StepFeature> steps) {

        List<TutorialStep> tutorialSteps = new ArrayList<>();

        for (StepFeature step : steps) {

            TutorialStep tutorialStep = new TutorialStep();
            tutorialStep.setContentType(getContentType(step));
            tutorialStep.setContentParams(getContentParams(step));

            tutorialStep.setConquestType(getConquestType());
            tutorialStep.setConquestParams(getConquestParams(step));

            tutorialSteps.add(tutorialStep);
        }

        tutorialData.setSteps(tutorialSteps);
    }

    private String getContentType(StepFeature step) {
        return CONTENT_TYPE.getContentTypeFor(step.getContentType());
    }

    private ImmutableMap<String, String> getContentParams(StepFeature step) {
        return ImmutableMap.of("content", step.getContent(), "name", step.getName());
    }

    public String getConquestType() {
        String conquestType = CONQUEST_TYPE.NONE.name();

        if (conquestFeature.isPresent()) {
            conquestType = CONQUEST_TYPE.getConquestTypeFor(conquestFeature.get().getTypeName());
        }

        return conquestType;
    }

    private Map<String, String> getConquestParams(StepFeature step) {

        Map<String, String> conquestParams = new HashMap<>();

        if (conquestFeature.isPresent()) {
            conquestParams.putAll(conquestFeature.get().getType().getParams(step));
        }

        conquestParams.putAll(step.getConquestParams());


        return conquestParams;
    }

    public void setConquestFeatureIfExists(ConquestFeature conquestTypeIfExists) {
        conquestFeature = Optional.fromNullable(conquestTypeIfExists);
        if (conquestFeature.isPresent()) {
            tutorialData.setRepository(conquestFeature.get().getRepository());
        }
    }


}
