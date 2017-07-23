
(function () {
    var Jquery = null;
    var approvedScopes = [];
    function loadScript(url, callback) {

        var script = document.createElement("script")
        script.type = "text/javascript";

        if (script.readyState) { //IE
            script.onreadystatechange = function () {
                if (script.readyState == "loaded" || script.readyState == "complete") {
                    script.onreadystatechange = null;
                    Jquery = $;
                    callback();

                }
            };
        } else { //Others
            script.onload = function () {
                Jquery = $;
                callback();
            };
        }

        script.src = url;
        document.getElementsByTagName("head")[0].appendChild(script);
    }


    function isValidUrl(str) {
       
            return true;
        
    }


    function replaceAll(find, replace, str) {
        return str.replace(new RegExp(find, 'g'), replace);
    }


    var INPUT_TEXT_TEMPLATE = '<div class="fitem required fitem_ftext " id="fitem_id_name">' +
        '<div class="fitemtitle"><label for="id_name">:field_label<img src="http://dev.broadecos/moodle/theme/image.php/clean/core/1439654657/req" alt="Required field" title="Required field" class="req"> </label>' +
        '<span class="helptooltip">' +
        '<a target="_blank" aria-haspopup="true" title="Help with :field_label" href="http://dev.broadecos/moodle/help.php?component=broadecosmod&amp;identifier=:field_id&amp;lang=en"><img class="iconhelp" alt="Help with :field_label" src="http://dev.broadecos/moodle/theme/image.php/clean/core/1439654657/help"></a>' +
        '</span>' +
        '</div>' +
        '<div class="felement ftext">' +
        '<span id="id_custom_error_:field_id" class="error" tabindex="0" style="display:none""></span>'+
        '<br id="id_custom_error_br_:field_id" class="error" style="display:none">' +
        '<input type="text" id="id_:field_id" onchange="validate_mod_broadecosmod_mod_form_name(this, \':field_id\')" onblur="validate_mod_broadecosmod_mod_form_name(this, \':field_id\')" name=":field_id" size="64">' +
        '</div>' +
        '</div>';


    var INFO_TEXT_TEMPLATE = '<div class="fitem servicemetadata" >' +
        '<div class="fitemtitle" >' +
            '<div class="fstaticlabel" >:metadata</div>' +
        '</div>' +
        '<div class="felement fstatic">:value</div>ser' +
        '</div>';

    var INFO_TEXT_TEMPLATE = '<div class="fitem servicemetadata" >' +
        '<div class="fitemtitle" >' +
        '<div class="fstaticlabel" >:metadata</div>' +
        '</div>' +
        '<div class="felement fstatic">:value</div>' +
        '</div>';

    var SCOPES_TEMPLATE = '<div class="fitem fitem_fcheckbox servicemetadata">'+
            '<div class="fitemtitle">'+
            '<label >Este serviço solicita as seguintes permissões:</label>'+
            '</div>'+
            '<div id="scopes" class="felement fcheckbox servicemetadata" >'+
            '</div>'+
    '</div>';


    var EXTENSIONS_TEMPLATE = '<div class="fitem fitem_fcheckbox servicemetadata">'+
        '<div class="fitemtitle">'+
        '<label >Este serviço solicita os seguintes escopos de extensões:</label>'+
        '</div>'+
        '<div id="extensions" class="felement fcheckbox servicemetadata" >'+
        '</div>'+
        '</div>';




    function loadServiceMetadata(serviceUri, changed, callback) {
        callback = callback || function(){};
        if (serviceUri){
            var $errorDb = Jquery('#id_custom_error_br_external_service_uri');
            var $error = Jquery('#id_custom_error_external_service_uri');

            Jquery('.servicemetadata').remove();
            $errorDb.hide();
            $error.hide();

            if (isValidUrl(serviceUri)){

                Jquery.getJSON( serviceUri+'/metadata', function(metadata) {

                    var infos = [];
                    infos.push(replaceAll(':value', metadata.name, replaceAll(':metadata', 'Nome', INFO_TEXT_TEMPLATE)));
                    infos.push(replaceAll(':value', metadata.description, replaceAll(':metadata', 'Descrição', INFO_TEXT_TEMPLATE)));
                    infos.push(SCOPES_TEMPLATE);
                    infos.push(EXTENSIONS_TEMPLATE);


                    Jquery.each(infos, function(i, it){
                        Jquery('#id_serviceconfig > div:last').append(it);
                    });

                    var scopes = [];
                    var xscopes = [];
                    var previdousApprovedScopes = [];

                    if (!changed) {
                        previdousApprovedScopes = (Jquery('[name=broadecos_activity_scopes]').val() || "").split(',');
                    }

                    Jquery.each(metadata.scopes, function(i, scope){
                        var cheched = '';

                        if (Jquery.isPlainObject(scope))
                            scope = scope.id;

                        if (Jquery.inArray(scope, previdousApprovedScopes)!= -1){
                            cheched = 'checked="checked"';
                        }

                        scopes.push('<span><input name="'+scope+'" '+cheched+' class="broadescosscope" type="checkbox" value="'+scope+'" id="'+scope+'">'+scope+'</span>');
                    });

                    Jquery('#scopes').html(scopes.join('<br />'));

                    console.log(metadata);
                    Jquery.each(metadata.extensions, function(i, extension){
                        var cheched = '';

                        Jquery.each(extension.scopes, function(e, xscope) {

                            if (Jquery.isPlainObject(xscope))
                                xscope = xscope.id;


                            if (Jquery.inArray(xscope, previdousApprovedScopes) != -1) {
                                cheched = 'checked="checked"';
                            }

                            xscopes.push('<span><input name="'+xscope+'" '+cheched+' class="broadescosscope" type="checkbox" value="'+xscope+'" id="'+xscope+'">('+extension.id+') - '+xscope+'</span>');
                        });
                    });

                    Jquery('#extensions').html(xscopes.join('<br />'));

                    $scopes = Jquery('.broadescosscope');
                    $scopes.change(function(){
                        approvedScopes.length=0;

                        $scopes.each(function(){
                            if (Jquery(this).prop("checked")){
                                approvedScopes.push(Jquery(this).val());
                            }
                        });

                        Jquery('[name=broadecos_activity_scopes]').val(approvedScopes.join(','));
                    });

                    Jquery('[name=external_service_entrypoint]').val(metadata.entryPoint);
                    Jquery('[name=external_service_id]').val(metadata.id);
                    console.log(Jquery('[name=external_service_id]').val(), metadata.id);
                    callback(null, metadata);

                }).fail(function() {
                    $error.html('Error loading \''+serviceUri+'\'');
                    $error.show();
                    $errorDb.show();
                    callback(arguments, null);
                });
            }  else {
                $error.html('You should provide a valid URI, like \'http://myservice.com\'');
                $error.show();
                $errorDb.show();
            }
        }
    }

    var externalServiceUriHtml =  replaceAll(':field_label', 'Endereço do Serviço Externo', INPUT_TEXT_TEMPLATE);
    externalServiceUriHtml =  replaceAll(':field_id', 'external_service_uri', externalServiceUriHtml);

    loadScript("https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js", function () {

        $('#id_serviceconfig > div:first').prepend(externalServiceUriHtml);

        $externalURI = $('#id_external_service_uri');

        var instance = $('[name=instance]');

        var initialServiceUri = '';
        if (instance) {
            initialServiceUri = $('[name=external_service_uri]').val();
            $externalURI.val(initialServiceUri);
            loadServiceMetadata(initialServiceUri, false);
        }



        $externalURI.blur(function(){
            var $input =  Jquery('#id_external_service_uri');

            var serviceUri = $input.val();
            if (serviceUri != initialServiceUri) {
                loadServiceMetadata(serviceUri, true);
                initialServiceUri = serviceUri;
            }

        });

    });


})();



