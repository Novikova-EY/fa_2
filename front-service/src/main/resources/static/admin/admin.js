angular.module('market').controller('adminController', function ($scope, $http, $location) {
    const corePath = 'http://localhost:5555/core';

    $scope.checkRole = function () {
        $http({
            url: corePath + '/api/v1/admin/check',
            method: 'GET'
        }).then(function successCallback(response) {
            console.log(response);
            $scope.answer = response.data;
        }, function errorCallback(response) {
            console.log(response);
            alert(response.data.message);
            $location.path('/');
        });
    }

    $scope.checkRole();
});