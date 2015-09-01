'use strict';

(function() {
    var app = angular.module('taughtology')
    app.controller('SendChallengeCtrl', ['$scope', 'challengeQuestion', 'Classmate', 'gameService', function ($scope, challengeQuestion, Classmate, gameService) {


        Classmate.query({}).$promise.then(function(data){
            $scope.classmates = {length: data.length};
            _.reduce(data, function (o, classmate) {
                if (!o[classmate.course.id])
                    o[classmate.course.id] = {course: classmate.course, classmates: []};
                o[classmate.course.id].classmates.push(classmate);
                return o;

            }, $scope.classmates);

            console.log($scope.classmates);
        });

        $scope.question = challengeQuestion;
        $scope.classmates = false;
        $scope.levelName = gameService.levelName;



    }]);
})();