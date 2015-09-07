'use strict';

(function() {
    var app = angular.module('taughtology')
    var ChallengeCtrl = function ($rootScope, $scope, $location, Answer,  challenge, Challenge, userService) {

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
               Answer.save({
                    "user":user,
                    "question": challenge.question,
                    "answeredOption":answer,
                    "correct":answer == challenge.question.solution
                }).$promise.then(function(savedAnswer){
                        $rootScope.$broadcast('questionAnswered', savedAnswer);
                        var answerId = savedAnswer.id;

                        challenge.accepted = true;
                        challenge.met = savedAnswer.correct;
                        challenge.$save().then(function(challenge){
                            $location.path('/challenge-feedback/'+challenge.id);
                        });
                });
            });
        };

        $scope.levelName = levelName;
        $scope.answer = answer;
        $scope.challenge=challenge;

    };

    app.controller('ChallengeCtrl', ['$rootScope', '$scope', '$location', 'Answer', 'challenge', 'Challenge', 'userService', ChallengeCtrl]);
})();