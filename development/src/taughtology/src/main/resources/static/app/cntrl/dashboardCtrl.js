'use strict';

(function() {
    var app = angular.module('taughtology');
    app.controller('DashboardCtrl', ['$scope', 'userService', 'gameService', 'Answer', 'Challenge', function ($scope, userService, gameService, Answer, Challenge) {

        var answers = {};

        userService.contextUser().then(function(user){
            Answer.query({user:user.id}, function(data){
                _.reduce(data, function (o, answer) {
                    if (!o[answer.question.level])
                        o[answer.question.level] = [];
                    o[answer.question.level].push(answer);
                    return o;
                }, answers);
            });

            Challenge.query({challenged:user.id}, function(data){
                $scope.receivedChallenges = data;
            });

            Challenge.query({challenged:user.id, accepted: true}, function(data){
                $scope.acceptedChallenges = data;
            });

            Challenge.query({challenger:user.id}, function(data){
                $scope.sentChallenges = data;
            });
        });


        $scope.answers = answers;
        $scope.levelName = gameService.levelName;
        $scope.receivedChallenges = [];
        $scope.acceptedChallenges = [];
        $scope.sentChallenges = [];
    }]);
})();