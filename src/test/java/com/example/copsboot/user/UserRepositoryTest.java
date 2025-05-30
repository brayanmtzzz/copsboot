// package com.example.copsboot.user;

// import com.example.orm.jpa.InMemoryUniqueIdGenerator;
// import com.example.orm.jpa.UniqueIdGenerator;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
// import org.springframework.boot.test.context.TestConfiguration;
// import org.springframework.context.annotation.Bean;

// import java.util.HashSet;
// import java.util.Set;
// import java.util.UUID;

// import static org.assertj.core.api.Assertions.assertThat;

// @DataJpaTest
// public class UserRepositoryTest {

//     @Autowired
//     private UserRepository repository;

//     @Test
//     public void testStoreUser() {
//         Set<UserRole> roles = new HashSet<>();
//         roles.add(UserRole.OFFICER);

//         User user = repository.save(
//             new User(repository.nextId(), "alex.foley@beverly-hills.com", "my-secret-pwd", roles)
//         );

//         assertThat(user).isNotNull();
//         assertThat(repository.count()).isEqualTo(1L);
//     }

//     @TestConfiguration
//     static class TestConfig {
//         @Bean
//         public UniqueIdGenerator<UUID> generator() {
//             return new InMemoryUniqueIdGenerator();
//         }
//     }
// }
