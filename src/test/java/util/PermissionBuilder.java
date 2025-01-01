package util;

import com.julian.commerceauthsecurity.domain.models.Permission;
import com.julian.commerceauthsecurity.domain.valueobject.SecurityName;
import com.julian.commerceauthsecurity.infrastructure.entity.PermissionEntity;

import java.util.UUID;

public class PermissionBuilder {
    public static Permission createPermissionWithParameters(String name) {
        return Permission.create(UUID.randomUUID(), SecurityName.create(name));
    }

    public static Permission createPermissionWithRandomName() {
        return Permission.create(UUID.randomUUID(), SecurityName.create("permission"));
    }

    public static PermissionEntity createPermissionEntityWithParameters(String name) {
        return new PermissionEntity(UUID.randomUUID(), name);
    }

    public static PermissionEntity createPermissionEntityWithRandomName() {
        return new PermissionEntity(UUID.randomUUID(), "permission");
    }
}
