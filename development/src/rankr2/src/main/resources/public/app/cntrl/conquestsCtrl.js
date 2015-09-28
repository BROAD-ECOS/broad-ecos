'use strict';

(function() {
    var app = angular.module('rankr');
    app.controller('ConquestsCtrl', ['$scope', 'Conquest',  '$moment', 'ConquestCount', function ($scope, Conquest,  $moment, ConquestCount) {
        var countConquests = function(user){
            user.conquestsCount = ConquestCount.get({id: user.id});
        };

        $scope.fromNow = $moment.fromNow;

        $scope.conquests = Conquest.get({'page':0, 'pageSize':100});


    }]);
})();