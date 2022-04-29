angular.module('market').controller('createProductController', function ($scope, $http, $localStorage, $location) {
    const corePath = 'http://localhost:5555/core';

    $scope.createNewProduct = function () {
        $http.post(corePath + '/api/v1/products', $scope.newProduct)
            .then(function () {
                $scope.newProduct = null;
                alert('Продукт создан');
                $location.path('/market');
            });
    }
});
