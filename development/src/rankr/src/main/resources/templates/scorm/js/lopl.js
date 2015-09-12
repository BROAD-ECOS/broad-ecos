var CONTENT_TYPE = {
    'PLAIN_TEXT' : function(params) {
        
        var _props = params;
        
        this.getName = function() {
            return _props.name;
        };
        
        this.getTemplate = function() {
            return 'plain_text_template';
        }
        
        this.getScope = function() {
            return {
                content: _props.content
            };
        }
    },
    'RICH_TEXT' : function(params) {

            var _props = params;

            this.getName = function() {
                return _props.name;
            };

            this.getTemplate = function() {
                return 'rich_text_template';
            }

            this.getScope = function() {
                return {
                    content: _props.content
                };
            }
    },
    'IMAGE' : function(params) {

          var _props = params;

          this.getName = function() {
              return _props.name;
          };

          this.getTemplate = function() {
              return 'image_template';
          }

          this.getScope = function() {
                return {
                  content: _props.content
              };
          }
      },
    'VIDEO' : function(params) {
          var _props = params;

          this.getName = function() {
              return _props.name;
          };

          this.getTemplate = function() {
              return 'video_template';
          }

          this.getScope = function() {
              var  getId = function(url) {
                var regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|\&v=)([^#\&\?]*).*/;
                var match = url.match(regExp);

                if (match && match[2].length == 11) {
                    return match[2];
                } else {
                    return 'error';
                }
            };

              return {
                  content: getId(_props.content)
              };
          }
        }
    };

var CONQUEST_TYPE = {
    'POINTS' : function(params) {
        return function(index) {
            if (params.points){
                var template = $('#points_template').html();
                var $container = $('#modal_container');
                $container.html(Mustache.render(template, {points: params.points}));
                $container.modal();
                lo.api.sendConquest(lo.stepNames[index], 'points', params.points);
            }
        };
    },
    'MEDALS' : function(params) {
        return function(index) {
            if (params.medal){
                var template = $('#medals_template').html();
                var $container = $('#modal_container');
                $container.html(Mustache.render(template, {
                    "medal": params.medal,
                    "medalName":params.medalName,
                    "value":params.value
                }));
                $container.modal();
                lo.api.sendConquest(lo.stepNames[index], 'medals', params.value);
            }
        };
    },
    'NONE' : function(){ return function() {}}
};


function ScormHandler(scorm, logEnabled){
    
    this.scorm = scorm;
    
    this.logEnabled = logEnabled || true;
    
    function log () {
        if (logEnabled)
            console.log(arguments)
        return arguments[0];
    }
    
    this.init = function () {
        log(this.scorm.init());
        this.set("cmi.core.lesson_location", '0');
    };
    
    this.setVersion = function(version) {
        log("Versão da API: "+this.scorm.init());
    };
    
    this.set = function (param, value) {
        log(this.scorm.set(param, value));
    };
    
    this.get = function (param) {
        return log(this.scorm.get(param));
    };
    
    this.complete = function() {
        this.set("cmi.completion_status", "completed");
    };
        
    this.quit = function(param) {
        log(this.scorm.quit());
    };
};


function LOPL_API(repository, loId, learnerId, learnerName) {

    this.repository = repository;

    this.loId = loId;

    this.learnerId = learnerId;

    this.learnerName = learnerName;

    this.sendConquest = function(interaction, conquestType, conquestValue) {
        var url  =  this.repository
            +'?id='+this.learnerId
            +'&name='+this.learnerName
            +'&loId='+this.loId
            +'&interaction='+interaction
            +'&conquest='+conquestType
            +'&value='+conquestValue;

        $('#gatewayframe').html('<iframe name="gateway" width="1" height="1" style=" border:none;overflow:auto;" src="'+url+'"></iframe>');
     };
}


function LO (scorm, api) {
    
    this.learner_id = '0';

    this.learner_name = 'Anônimo';

    this.stepNames = [];

    this.sco = new ScormHandler(scorm);
    
    /*
    * Instanciando e configurando barra de progresso.
    */
    var createCircularProgress = function () {
        var progress =  new CircularProgress({
              radius: 30,
              strokeStyle: 'black',
              lineCap: 'round',
              lineWidth: 4
        });
    
        $('#progress').append(progress.el);
    
        progress.update(0);
        
        return progress;
    };
    
    /**
    * Instanciando e configurando wizard do tutorial.
    */
    var createTutorialWizard = function () {
            
        var handleNext = function(index) {
            lo.handlers[index](index);
            lo.handlers[index] = CONQUEST_TYPE['NONE'];
        };
        
        var sco = this.sco;
        $('#rootwizard').bootstrapWizard({
            sco: sco,
            tabClass: 'nav nav-tabs',
            nextSelector: '.button-next', 
            previousSelector: '.button-previous',
            finishSelector: '.button-finish',
            
            // Dispara as conquistas paraa etapa desbloqueada
            onNext : function($activeTab, $navigation, nextIndex) {
                handleNext(nextIndex-1);
                return true;
            },
            
            // Habilita as etapas uma a uma
            onTabChange: function($activeTab, $navigation, currIdx, nextIdx) {
                var nextNext = nextIdx+1;
                var $nextNext = $($navigation.find('li')[nextNext]);
                
                if ($nextNext.hasClass('disabled')) {
                    $('#rootwizard').bootstrapWizard('enable', nextNext);
                }
                
                if (currIdx + 1 == $navigation.find('li').length) {
                    $('#rootwizard').find('.button-next').hide();
                    $('#rootwizard').find('.button-previous').hide();
                    $('#rootwizard').find('.button-finish').show();
                } else {
                    $('#rootwizard').find('.button-previous').show();
                    $('#rootwizard').find('.button-next').show();
                    $('#rootwizard').find('.button-finish').hide();
                }
                
                return true;
            },
            
            // Desabilitando cliques que pulem etapas do tutorial
            onTabClick: function(tab, $navigation, index, clickedIndex, $clickedTab) {
                if (!$clickedTab.hasClass('disabled')) {
                    handleNext(clickedIndex);
                    return true;
                }
                return false;
            },
            
            // Atualizando barra de progresso.
            onTabShow : function(tab, navigation, index) {
                var current = index+1;
                var percent = (current/self.steps) * 100;

                if (percent > lo.progress._percent)
                    lo.progress.update(percent);

                var scoProgress = function(current, total) {
                    if (current<=1) {
                        return "not attempted";
                    } else if (current < total) {
                        return "incomplete";
                    } else {
                        return "completed";
                    }
                };

                lo.sco.set('cmi.completion_status', (current/self.steps));
                lo.sco.set('cmi.progress_measure', scoProgress(current, self.steps));
            }
        });	   
        
        // Desabilitando as etapas posteriores
        steps = $('.nav-tabs li').length;
        for (var i=2; i < parent.steps; i++) {
            $('#rootwizard').bootstrapWizard('disable', i);
        }
    };
    
    var bindEvents = function(scorm) {
        
        $('#rootwizard .button-finish').click(function() {
            var template = $('#finish_template').html();
            var $container = $('#modal_container');
            $container.html(Mustache.render(template, {}));
            $container.modal(); 
            lo.sco.complete();
            lo.sco.quit();
        });
    };
    
    this.currIdx = 0;
    this.handlers = [];
    
    /**
    * Método que inicializa todas as dependências e prepara o tutorial.
    */
    this.init = function(repository, loId) {
            
        window.prettyPrint && prettyPrint();
            
        this.sco.setVersion("2004");
            
        this.progress = createCircularProgress();
            
        createTutorialWizard();
        
        bindEvents();
            
        this.sco.init();

        this.learner_id = this.sco.get('cmi.learner_id') || this.learner_id;
        this.learner_name = this.sco.get('cmi.learner_name') || this.learner_name;
        this.repository = repository;
        this.loId = loId;

        this.api = new LOPL_API(this.repository, this.loId, this.learner_id, this.learner_name);
    };
    
    this.addStepName = function (name) {
        var template = $('#step_name_template').html();
        var data = {name: name, idx: this.currIdx}

        this.stepNames.push(name);

        $('#navigation').append(Mustache.render(template, data));
    };
    
    
    this.addStepContent = function (template, scope) {
       
        var baseTemplate = $('#step_content_template').html();
        
        var htmlContent = Mustache.render($('#'+template).html(), scope);
        var htmlBase = Mustache.render(baseTemplate, {content: htmlContent, idx: this.currIdx})
        
        $('#content').append(htmlBase);
    };
    
    this.addStep = function (stepType, stepParams, conquestType, conquestParams) {
        
        var step = new CONTENT_TYPE[stepType](stepParams);
        
        this.addStepName(step.getName());
        this.addStepContent(step.getTemplate(), step.getScope());
        
        this.handlers[this.currIdx] = CONQUEST_TYPE[conquestType](conquestParams);
        this.currIdx++;
    };
};


var lo = new LO(pipwerks.SCORM);
