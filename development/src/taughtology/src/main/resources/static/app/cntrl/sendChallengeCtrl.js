'use strict';

(function() {

    var SendChallengeCtrl = function ($scope, $modal,  challengeQuestion, Classmate, Challenge,  gameService, userService) {

        var sendChallenge = function(user) {
            var modal = $modal.open({
                animation:true,
                templateUrl: 'sendChallengeModal.html',
                controller: ['$scope', '$modalInstance', function($modalScope, $modalInstance){

                    $modalScope.ok = function () {
                        $modalInstance.close(user);
                    };

                    $modalScope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };

                    $modalScope.user = user;
                }]
            });

            modal.result.then(function (challenger) {
                userService.contextUser().then(function(challenged){
                    return Challenge.save({
                        challenger: challenger,
                        challenged: challenged,
                        question: challengeQuestion,
                        met: false
                    }).$promise;
                }).then(function(challenge){
                    console.log(challenge);
                });

            }, function () {
                // Cancelado
            });
        };

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

        $scope.sendChallenge = sendChallenge;

    };


    var app = angular.module('taughtology');
    app.controller('SendChallengeCtrl', ['$scope', '$modal', 'challengeQuestion', 'Classmate', 'Challenge', 'gameService', 'userService', SendChallengeCtrl]);
})();