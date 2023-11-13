package ua.house.book.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.house.book.auth.config.TestAuthConfig;
import ua.house.book.auth.config.TestHibernateConfig;
import ua.house.book.auth.domain.dto.request.AuthorizationDTO;
import ua.house.book.auth.domain.dto.request.RegistrationDTO;
import ua.house.book.auth.domain.entity.Account;
import ua.house.book.auth.service.AuthService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestHibernateConfig.class, TestAuthConfig.class})
class AuthControllerTest {
    @InjectMocks
    private AuthController authController;
    @Mock
    private AuthService authService;
    private MockMvc mockMvc;
    @Autowired
    private Account userAccount;
    @Autowired
    private Account adminAccount;
    @Autowired
    private RegistrationDTO userAccountRegistrationDtoRequest;
    @Autowired
    private RegistrationDTO adminAccountRegistrationDtoRequest;
    @Autowired
    private AuthorizationDTO userAuthorizationDtoRequest;
    @Autowired
    private AuthorizationDTO adminAuthorizationDtoRequest;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testOrdinalRegistrationHttp() throws Exception {
        // Prepare the request data
        // Perform the HTTP POST request to the endpoint
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/users/registration")
                        .content(asJsonString(userAccountRegistrationDtoRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void testAdminRegistrationHttp() throws Exception {
        // Prepare the request data
        // Perform the HTTP POST request to the endpoint
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/admins/registration")
                        .content(asJsonString(adminAccountRegistrationDtoRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void testAdminAuthorizationHttp() throws Exception {
        given(authService.authorization(ArgumentMatchers.any()))
                .willReturn(adminAccount);
        // Perform the HTTP POST request to the endpoint
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/authorization")
                        .content(asJsonString(adminAuthorizationDtoRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testOrdinalAuthorizationHttp() throws Exception {
        given(authService.authorization(ArgumentMatchers.any()))
                .willReturn(userAccount);
        // Perform the HTTP POST request to the endpoint
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/authorization")
                        .content(asJsonString(userAuthorizationDtoRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // Helper method to convert an object to JSON
    private String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}