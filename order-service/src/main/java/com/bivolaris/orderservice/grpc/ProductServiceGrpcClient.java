package com.bivolaris.orderservice.grpc;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import products.*;

import java.util.UUID;

@Service
public class ProductServiceGrpcClient {

    private final ProductServiceGetGrpc.ProductServiceGetBlockingStub blockingStub;

    public ProductServiceGrpcClient(
            @Value("${product.service.address:localhost}") String serverAddress,
            @Value("${product.service.grpc.port:8002}") int serverPort) {


        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress,
                serverPort).usePlaintext().build();

        blockingStub = ProductServiceGetGrpc.newBlockingStub(channel);
    }


    public GetProductResponse getProductById(UUID productId){

        String productIdStr = productId.toString();

        GetProductRequest request = GetProductRequest.newBuilder()
                .setProductId(productIdStr)
                .build();


        return blockingStub.getProductById(request);

    }

    public GetStockResponse getStockLevel(UUID productId){

        String productIdStr = productId.toString();

        GetStockRequest request = GetStockRequest.newBuilder()
                .setProductId(productIdStr)
                .build();

        return blockingStub.getStockLevel(request);
    }


}
