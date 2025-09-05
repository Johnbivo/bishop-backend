package com.bivolaris.userservice.grpc;


import billing.AccountBillingRequest;
import billing.AccountBillingResponse;
import billing.AccountBillingServiceGrpc;
import com.bivolaris.userservice.dtos.BillingAccountResponse;
import com.bivolaris.userservice.dtos.CreateBillingAccountRequest;
import com.bivolaris.userservice.entities.BillingAccountStatusEnum;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import billing.DeleteBillingAccountRequest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class AccountBillingService {

    private final AccountBillingServiceGrpc.AccountBillingServiceBlockingStub blockingStub;

    public AccountBillingService(
            @Value("${BILLING.SERVICE.ADDRESS:LOCALHOST}") String serverAddress,
            @Value("${BILLING.SERVICE.GRPC.PORT:9001}") int serverPort) {



        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress,
                serverPort).usePlaintext().build();

        blockingStub = AccountBillingServiceGrpc.newBlockingStub(channel);
    }

    public BillingAccountResponse createBillingAccount(CreateBillingAccountRequest request) {

        // Build gRPC request from DTO
        AccountBillingRequest grpcRequest = AccountBillingRequest.newBuilder()
                .setUserId(request.getUserId().toString())
                .setCurrency(request.getCurrency().name())
                .build();

        // Call the billing service
        AccountBillingResponse grpcResponse = blockingStub.createBillingAccount(grpcRequest);

        // Convert gRPC response to internal DTO
        BillingAccountResponse response = new BillingAccountResponse();
        response.setBillingAccountId(UUID.fromString(grpcResponse.getBillingAccountId()));
        response.setBillingAccountStatus(
                BillingAccountStatusEnum.valueOf(grpcResponse.getBillingAccountStatus())
        );
        response.setCreatedAt(LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(grpcResponse.getCreatedAt().getSeconds(),
                                grpcResponse.getCreatedAt().getNanos()),
                        ZoneOffset.UTC
                )
        );

        return response;
    }



    public boolean deleteBillingAccount(String billingAccountId) {
        DeleteBillingAccountRequest grpcRequest = DeleteBillingAccountRequest.newBuilder()
                .setBillingAccountId(billingAccountId)
                .build();

        try {
            blockingStub.deleteBillingAccount(grpcRequest);
            return true;
        } catch (io.grpc.StatusRuntimeException e) {
            if (e.getStatus().getCode() == io.grpc.Status.Code.NOT_FOUND) return false;
            throw e;
        }
    }





}
