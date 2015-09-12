/**
 * Created by Maikel Rivero Dorta mriverodorta@gmail.com on 7/08/14.
 */
"use strict";
angular.module('lopl')
    .controller('LoListCtrl', ['$scope', '$location', 'LoService', 'moment', function ($scope, $location, loService, moment) {

        $scope.status = "Carregando";
        $scope.los = [];

        loService.all().then(function(los) {
            var transformedLos = [];
            angular.forEach(los, function(lo){
                lo.updatedAtFromNow = moment(lo.updatedAt).fromNow();
                transformedLos.push(lo);
            });

            $scope.los = transformedLos;
            $scope.status = false;
        });

        $scope.newLo = function () {
            $location.path('autoring/new');
        };

        $scope.edit = function(id){
            $location.path('/autoring/edit/'+id);
        }
        
    }])
