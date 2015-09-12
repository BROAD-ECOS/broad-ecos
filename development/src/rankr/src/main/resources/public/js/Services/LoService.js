'use strict';

angular.module('lopl')
    .factory('LoService', ['LO', '$q',
        function (LO, $q) {
            return {
                save: function (lo) {
                   return lo.$save();
               },
               get: function (id) {
                   var deferred = $q.defer()

                   LO.get({'id':id} , function(data){
                        deferred.resolve(data)
                   }, function(data){
                        deferred.resolve(data)
                   });

                   return deferred.promise;
               },
               all : function(){
                    return LO.query().$promise;
               }
            };
        }
])