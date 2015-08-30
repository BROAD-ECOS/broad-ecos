'use strict';

(function() {
    var app = angular.module('taughtology')
    var QuestionCtrl = function ($q, $rootScope, $scope, $location, Question, Answer, randomQuestion, userService) {

        $scope.question = randomQuestion;

        var levelName = function(level){

            var LEVEL_NAMES = {
                'EASY':'Fácil',
                'MEDIUM':'Médio',
                'HARD': 'Difícil',
                'INSANE':'Insano'
            };

            return LEVEL_NAMES[level] || "?";
        };

        var answer = function(answer) {
            userService.contextUser().then(function(user){
                var deferred = $q.defer();
                Answer.save({
                    "user":user,
                    "question": randomQuestion,
                    "answeredOption":answer,
                    "correct":answer == randomQuestion.solution
                }).$promise.then(function(data){
                        var answerId = data.id;
                        $rootScope.$broadcast('questionAnswered', data);
                        $location.path('/answer-feedback/'+answerId);
                });
            });
        };

        $scope.levelName = levelName;
        $scope.answer = answer;

    };

    app.controller('QuestionCtrl', ['$q','$rootScope', '$scope', '$location', 'Question', 'Answer', 'randomQuestion', 'userService', QuestionCtrl]);
})();