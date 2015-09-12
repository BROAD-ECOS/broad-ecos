package br.ufjf.nenc.lp.core.service;

import br.ufjf.nenc.lp.core.exception.NotFoundException;
import br.ufjf.nenc.lp.core.pl.*;
import br.ufjf.nenc.lp.core.pl.ir.ProductDescriptor;
import com.google.common.base.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class BuildService {

    public static final String FAILURE_BUILDING_PRODUCT = "Failure building product";

    public static final String RESPONSIBLE_BUILDER_NOT_FOUND = "Builder not found for product";

    @Value("${lo.build.directory}")
    private String buildDirectoryPath;

    @Autowired
    private FeatureAssembler assembler;

    @Autowired
    private BuilderHolder holder;

    public Product build(ProductDescriptor productDescriptor) {
        Product product = null;

        try {
            product = doBuild(productDescriptor);
        } catch (Exception e) {
            throw new ServiceException(FAILURE_BUILDING_PRODUCT, e);
        }

        return product;
    }

    private Product doBuild(ProductDescriptor productDescriptor) throws ProductLineException {
        Product product = null;

        Feature feature = assembler.assemble(productDescriptor);
        Optional<Builder> builderOptional = holder.getResponsibleBuilder(feature);

        if (builderOptional.isPresent()) {
            Builder builder = builderOptional.get();
            product = builder.build(feature);
        } else {
            throw new ServiceException(RESPONSIBLE_BUILDER_NOT_FOUND);
        }

        return product;
    }

    public File getBuildFile(String id) {

        File build = new File(buildDirectoryPath+ File.separator + id);

        if (!build.exists()) {
            throw new NotFoundException();
        }

        return build;
    }
}
