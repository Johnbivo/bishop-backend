
package com.bivolaris.orderservice.grpc;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import userServiceGrpc.GetUserRequest;
import userServiceGrpc.GetUserResponse;
import userServiceGrpc.UserServiceGrpc;

@Slf4j
@Service
public class UserServiceGrpcClient {

    private final UserServiceGrpc.UserServiceBlockingStub blockingStub;

    public UserServiceGrpcClient(
            @Value("${user.service.address:localhost}") String serverAddress,
            @Value("${user.service.grpc.port:8003}") int serverPort) {

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(serverAddress, serverPort)
                .usePlaintext()
                .build();

        blockingStub = UserServiceGrpc.newBlockingStub(channel);

        log.info("UserService gRPC client initialized: {}:{}", serverAddress, serverPort);
    }

    public GetUserResponse getUserWithAddresses(String userId) {
        try {
            log.info("Calling UserService gRPC to get user: {}", userId);

            GetUserRequest request = GetUserRequest.newBuilder()
                    .setUserId(userId)
                    .build();

            GetUserResponse response = blockingStub.getUserWithAddresses(request);

            log.info("Successfully retrieved user {} with {} addresses",
                    userId, response.getAddressesCount());

            return response;

        } catch (StatusRuntimeException e) {
            log.error("gRPC error calling UserService: {}", e.getStatus());
            throw new RuntimeException("Failed to get user data from UserService: " + e.getStatus().getDescription(), e);
        } catch (Exception e) {
            log.error("Error calling UserService gRPC: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get user data from UserService", e);
        }
    }
}