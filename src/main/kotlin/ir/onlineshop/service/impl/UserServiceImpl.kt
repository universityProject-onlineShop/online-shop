package ir.onlineshop.service.impl

import ir.onlineshop.common.exception.OnlineShopException
import ir.onlineshop.config.security.PasswordEncryption
import ir.onlineshop.database.model.User
import ir.onlineshop.database.model.enums.UserRole
import ir.onlineshop.database.repository.UserRepository
import ir.onlineshop.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl @Autowired constructor(
    private val userRepository: UserRepository
) : UserService {

    override fun save(user: User) {
//        val encoder = PasswordEncryption.getBCryptPasswordEncoder()
//        user.password = encoder.encode(user.password.trim())
//        encoder.encode(user.password)
        user.password="$2a$10$3lQ/Cz/OH9rlYRao4Je7AOqgsBbM9sH/sL8opWAroJTHpQHH2olca"
        userRepository.save(user)
    }

    override fun findById(userId: Long): User {
        return userRepository.findById(userId).orElseThrow { OnlineShopException("user with id $userId not found!") }
    }

    override fun findShopOwnerById(userId: Long): User {
        findById(userId).let {
            if (it.role!! != UserRole.SHOP_OWNER)
                throw OnlineShopException("user with id = $userId dont have SHOP_OWNER permission")
            return it
        }
    }

    override fun existById(userId: Long): Boolean {
        return userRepository.existsById(userId)
    }

}