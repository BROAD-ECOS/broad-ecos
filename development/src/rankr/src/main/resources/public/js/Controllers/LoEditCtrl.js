"use strict";

function parseFeature(node){

    var parsed = {};

    parsed.name = node.name;
    parsed.selected = node.selected;

    var param = [];

    angular.forEach(node.params, function(value,name) {
          param.push({"name":name, "value":value});
    });
    parsed.params = {"param": param};

    var features = [];
    angular.forEach(node.features, function(feat) {
         if (angular.isArray(feat) ) {
            angular.forEach(feat, function(featElm) {
                var feature = parseFeature(featElm);
                features.push(feature);
            });
         } else {
            var feature = parseFeature(feat);
            features.push(feature);
         }

    });

    parsed.feature = features;

    return parsed;
};

function formatFeatureModel(featureModel, lo){

    var format = (function format(node, loNode, loParent){

        var newNode = {};

        if (angular.isArray(node) ) {
            newNode = [];
            var name = node[0];
            if (loParent && loParent.feature)
            {
                for (var j=0; j < loParent.feature.length; j++) {
                    if (loParent.feature[j].name == name) {
                        newNode.push(format({name:name}, loParent.feature[j]));
                    }
                }
            }

        } else {

            if (!loNode)
                loNode = {};

            newNode.selected = loNode.selected || false;
            newNode.required = node.required || false;
            newNode.validate = node.validate || function(){return []};
            newNode.pending  = node.pending || 0;
            newNode.name  = node.name;
            newNode.params  = {};
            newNode.features  = {};

            if (loNode.params)
            {
                angular.forEach(loNode.params.param, function(param){
                    newNode.params[param.name] = param.value;
                });
            }

            angular.forEach(node.features, function(feat, featName){
                var loFeat = false;

                if (loNode && loNode.feature)
                {
                    for (var j=0; j < loNode.feature.length; j++) {

                        if (loNode.feature[j].name == feat.name) {
                            loFeat = loNode.feature[j];
                            break;
                        }
                    }
                }

                newNode.features[featName] = format(feat,loFeat,loNode);
            });
        }

        return newNode;
    });

    var features = {};
    angular.forEach(featureModel, function(feat, featName){
        var loFeat = false;
        if (lo.feature){
            for (var j=0; j < lo.feature.length; j++) {

                if (lo.feature[j].name == feat.name) {
                    loFeat = lo.feature[j];
                    break;
                }
            }
        }
        features[featName] = format(feat, loFeat);
    });

    return features;
};


var featureModel = {
    "metadata":{
        "name":"br.ufjf.nenc.lp.featuremodel.lo.metadata",
       "selected":false,
       "required": true,
       "features":{
           "lom": {
               "name":"br.ufjf.nenc.lp.featuremodel.lo.lom",
               "selected":false,
                "params":{},
                "pending":0,
                "validate": function(self, root){
                   var valid = [];

                   if (!self.params.title) {
                       valid.push("O parâmetro 'Title' do LOM é obrigatório e não foi informado.")
                   }

                   return valid;
                }
           }
        },
        "validate": function(self, root){
             var selected = function (value) { return value?1:0;}
             var valid = [];
             var total = selected(self.features.lom.selected);

             if (total > 1) {
                valid.push("Apenas uma das opções pode ser selecionada para a característica Metadados!");
             } if (total == 0) {
                valid.push("É obrigatório a escolha de uma das opções para a característica Metadados!");
             }

             return valid;
         }
    },
    "conquest":{
       "selected":false,
       "params":{},
       "name":"br.ufjf.nenc.lp.featuremodel.lo.conquest",
       "features":{
           "points": {
               "name":"br.ufjf.nenc.lp.featuremodel.lo.points",
               "params":{},
               "selected":false,
               "pending":0,
               "validate": function(self, root){
                   var valid = [];

                   if (!self.params.name) {
                       valid.push("O parâmetro 'Nome' de Pontos é obrigatório e não foi informado.")
                   }

                   return valid;
               }
           },
           "medals": {
                "name":"br.ufjf.nenc.lp.featuremodel.lo.medals",
                "params":{},
                "selected":false,
                "pending":0
           }
        },
        "validate": function(self, root){
            var selected = function (value) { return value?1:0;}
            var valid = [];
            var total = 0;

            total += selected(self.features.points.selected);
            total += selected(self.features.medals.selected);

            if (total > 1) {
               valid.push("Apenas uma das opções pode ser selecionada para a característica Gamificação!");
            } if (total == 0) {
               valid.push("É obrgatória a escolha de uma das opções para a característica Gamificação!");
            }

            if (!self.params.repository) {
                valid.push("É obrigatória a seleção de um repostório de dados na característica Gamificação");
            }

            return valid;
        }
    },
    "type":{
       "name":"br.ufjf.nenc.lp.featuremodel.lo.type",
       "selected":false,
       "required":true,
       "features":{
           "tutorial":{
               "name":"br.ufjf.nenc.lp.featuremodel.lo.tutorial.tutorial",
               "selected":false,
               "features":{
                   "index":{
                       "name":"br.ufjf.nenc.lp.featuremodel.lo.tutorial.index",
                       "selected":false
                   },
                   "sequential":{
                        "name":"br.ufjf.nenc.lp.featuremodel.lo.tutorial.sequential",
                        "selected":false
                   },
                   "steps": ["br.ufjf.nenc.lp.featuremodel.lo.tutorial.step"]
               },
               "validate": function(self, root){

                     var valid = [];

                     var steps = self.features.steps;

                     if (steps.length==0) {
                        valid.push('Na caraceterística "Tutorial" deve ter no mínimo um passo.');
                     } else {
                         for (var i=0; i < steps.length; i++) {
                            if (steps instanceof Object) {
                                if (!steps[i].params.name)
                                    valid.push('Na caraceterística "Tutorial" o parâmetro "Título" do Passo '+(i+1)+' é obrigatório e não foi preenchido.');
                                if (!steps[i].params.name)
                                    valid.push('Na caraceterística "Tutorial" o parâmetro "Conteúdo" do Passo '+(i+1)+' é obrigatório e não foi preenchido.');
                            }
                         }
                    }

                     return valid;
               }
           }
       },
       "validate": function(self, root){
            var selected = function (value) { return value?1:0;}
            var valid = [];
            var total = selected(self.features.tutorial.selected);

            if (total > 1) {
                valid.push("Apenas uma das opções pode ser selecionada para a característica Tipo!");
            } if (total == 0) {
                valid.push("É obrigatório a escolha de uma das opções para a característica Tipo!");
            }

            return valid;
        }
    },
    "package_format":{
       "name":"br.ufjf.nenc.lp.featuremodel.lo.packageformat",
       "selected":false,
       "required":true,
       "params":{},
       "features":{
           "scorm": {
               "name":"br.ufjf.nenc.lp.featuremodel.lo.scorm",
               "params":{},
               "selected":false,
               "pending":0,
               "validate": function(self, root){
                   var valid = [];

                   if (!self.params.format) {
                       valid.push("O parâmetro 'Especificação' da característica Scorm é obrigatório e não foi informado.")
                   }

                   return valid;
               }
           }
        },
        "validate": function(self, root){
            var selected = function (value) { return value?1:0;}
            var valid = [];
            var total = 0;

            total += selected(self.features.scorm.selected);

            if (total > 1) {
               valid.push("Apenas uma das opções pode ser selecionada para a característica Empacotamento!");
            } if (total == 0) {
               valid.push("É obrigatória a escolha de uma das opções para a característica Empacotamento!");
            }

            return valid;
        }
    }
};


angular.module('lopl')
    .controller('LoEditCtrl', ['LoService', 'ProductBuild', '$scope', '$routeParams', 'ngDialog', '$location', '$upload', function (loService, ProductBuild, $scope, $routeParams, ngDialog, $location,$upload) {

           $scope.status = "Carregando";
           $scope.lo = {};
           $scope.messages = [];


           loService.get($routeParams.id).then(function(lo){
                $scope.lo = lo;
                $scope.features = formatFeatureModel(featureModel, lo);
                $scope.status = false;
           });

           $scope.showFeatureModelDiagram = function() {
                ngDialog.open({
                    template: 'featureDiagram',
                    scope: $scope,
                    className: 'ngdialog-theme-default'
                });
           };

           $scope.defineLOMParams = function () {
                ngDialog.open({
                    template: 'lomParams',
                    scope: $scope,
                    className: 'ngdialog-theme-flat'
                });
           };

           $scope.defineConquestParams = function () {
               ngDialog.open({
                   template: 'conquestParams',
                   scope: $scope,
                   className: 'ngdialog-theme-flat'
               });
           };

           $scope.noParams = function () {
                ngDialog.open({
                    template: 'noParams',
                    scope: $scope,
                    className: 'ngdialog-theme-flat'
                });
           };

           $scope.definePointsParams = function () {
                ngDialog.open({
                    template: 'pointsParams',
                    scope: $scope,
                    className: 'ngdialog-theme-flat'
                });
           };

           $scope.defineTutorialParams = function () {
                $scope.showTutorialStep(0);
                ngDialog.open({
                    template: 'tutorialParams',
                    scope: $scope,
                    className: 'ngdialog-theme-flat'
                });
           };

            $scope.defineScormParams = function () {
                ngDialog.open({
                    template: 'scormParams',
                    scope: $scope,
                    className: 'ngdialog-theme-flat'
                });
            };

           $scope.changeSelection = function(target, checked) {
              if (checked) {
                    var path = $scope.features;

                    for (var i=0; i < target.length-1; i++) {
                         path[target[i]].selected = true;
                         path = path[target[i]].features;
                    }
              }

           };

          $scope.$watch('features', function(features, oldValue) {

                var features = {"features" : features, "selected": true};

                var messages = (function check(node){
                    var valid = [];

                    if (node.selected) {

                        if (node.validate) {
                            valid = node.validate(node, features);
                            node.pending = valid.length;
                        }

                        angular.forEach(node.features, function(feat) {
                             var childValid = check(feat);
                             valid = valid.concat(childValid);
                        });

                    } else {

                        if (node.required && node.validate) {
                            valid = node.validate(node, features);
                        }
                    }

                    return valid;
                })(features);

                $scope.messages = messages;
          } , true);

          $scope.addStep = function(contentType) {
                var step = {
                    params: {contentType:contentType, content:''},
                    name:'br.ufjf.nenc.lp.featuremodel.lo.tutorial.step',
                    features:[],
                    required:false,
                    selected:true
                };

                var newIndex = $scope.features.type.features.tutorial.features.steps.length;

                $scope.features.type.features.tutorial.features.steps.push(step);
                $scope.showTutorialStep(newIndex);
          };

          $scope.showTutorialStep = function(idx) {
               var steps = $scope.features.type.features.tutorial.features.steps;
               for (var i  = 0; i< steps.length; i++) {
                   steps[i].params.show = i == idx;
               }
          };

          $scope.save = function(callback) {

                callback = callback || function(){};

                $scope.status = "Salvando"

                var lo = $scope.lo;
                lo.feature = [];

                angular.forEach($scope.features, function(feature, name) {
                     lo.feature.push(parseFeature(feature));
                });

                loService.save(lo).then(function(data){
                    $scope.status = false
                    callback(data);
                });
          };

          $scope.download = function() {

              $scope.save(function(){
                  $scope.status = "Gerando..."

                  var product = {feature:[], name:'br.ufjf.nenc.lp.featuremodel.lo.learningobject', params:{param:[{"name": 'loId', "value": $scope.lo.id}]} };

                  angular.forEach($scope.features, function(feature, name) {
                       product.feature.push(parseFeature(feature));
                  });

                  var promise = new ProductBuild(product).$save();
                  promise.then(function(data){
                        window.location= '/pl/build/'+data.id;
                        $scope.status = false;
                  });

              });
          };


          $scope.back = function() {
               if (confirm("Você perderá todas as alterações não salvas, deseja continuar?")) {
                    $location.path('/autoring')
               }
          };

          $scope.selectMedalIco = function(step, medal) {
                step.params.medal = medal;
          };



    }])