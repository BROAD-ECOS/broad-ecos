'use strict';

(function() {
    var app = angular.module('taughtology');
    app.controller('profilePanelCtrl', ['$scope', 'userService', 'gameService', 'Broad', 'AchievementSummary', function ($scope, userService, gameService, Broad, AchievementSummary) {

        $scope.user = {};
        $scope.achievementsSummary = {};


        var loadData = function(){
            userService.contextUser().then(function (user) {
                $scope.user = user;
                $scope.achievementsSummary = AchievementSummary.get({id: user.id});
            });

            Broad.get({id: 'platform-info'}).$promise.then(function (platformInfo) {
                $scope.platformInfo = platformInfo;
            });

            Broad.get({id: 'course-info'}).$promise.then(function (course) {
                $scope.course = course;
            });
        };

        loadData();

        $scope.levelName = gameService.levelName;

        $scope.$on('questionAnswered', function(event) { console.log(event); loadData(); });

    }]);

})();