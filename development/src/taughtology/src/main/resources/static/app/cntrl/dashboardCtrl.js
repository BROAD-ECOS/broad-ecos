'use strict';

(function() {
    var app = angular.module('taughtology')
    app.controller('DashboardCtrl', ['$scope', 'userService', 'gameService', 'Answer', function ($scope, userService, gameService, Answer) {

        var answers = {};

        userService.contextUser().then(function(user){
            Answer.query({user:user.id}, function(data){
                _.reduce(data, function (o, answer) {
                    if (!o[answer.question.level])
                        o[answer.question.level] = [];
                    o[answer.question.level].push(answer);
                    return o;

                }, answers);


                console.log(answers);
            });

        });


        $scope.answers = answers;
        $scope.levelName = gameService.levelName;

    }]);
})();