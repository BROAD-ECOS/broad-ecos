'use strict';

(function() {
    var app = angular.module('rankr');
    app.controller('ProfileCtrl', ['$scope', 'Conquest', 'user', 'ConquestCount', function ($scope, Conquest, user, ConquestCount) {

        $scope.user = user;
        $scope.conquestsCount = ConquestCount.get({id: user.id});
        $scope.conquests = Conquest.get({userId:user.id, page:0, pageSize:100})
    }]);
})();