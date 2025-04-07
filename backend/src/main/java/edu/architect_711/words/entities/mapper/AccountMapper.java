package edu.architect_711.words.entities.mapper;

import edu.architect_711.words.entities.db.AccountEntity;
import edu.architect_711.words.entities.dto.AccountDto;

public class AccountMapper implements EntityMapper<AccountEntity, AccountDto> {

    @Override
    public AccountEntity toEntity(AccountDto dto) {
        return new AccountEntity(dto.getUsername(), dto.getPassword(), dto.getRole());
    }

    @Override
    public AccountDto toDto(AccountEntity entity) {
        return new AccountDto(entity.getUsername(), entity.getPassword(), entity.getRole());
    }

}
