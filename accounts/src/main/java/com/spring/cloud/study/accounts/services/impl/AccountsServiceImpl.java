package com.spring.cloud.study.accounts.services.impl;

import com.spring.cloud.study.accounts.exceptions.ResourceExistsException;
import com.spring.cloud.study.accounts.exceptions.ResourceNotFoundException;
import com.spring.cloud.study.accounts.mappers.AccountsMapper;
import com.spring.cloud.study.accounts.mappers.CustomerMapper;
import com.spring.cloud.study.accounts.models.dtos.AccountsDto;
import com.spring.cloud.study.accounts.models.dtos.CustomerDto;
import com.spring.cloud.study.accounts.models.entities.Accounts;
import com.spring.cloud.study.accounts.models.entities.Customer;
import com.spring.cloud.study.accounts.models.enums.AccountType;
import com.spring.cloud.study.accounts.repositories.AccountsRepository;
import com.spring.cloud.study.accounts.repositories.CustomerRepository;
import com.spring.cloud.study.accounts.services.IAccountsService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@FieldDefaults(level = AccessLevel.PACKAGE)
@Transactional(rollbackOn = Exception.class)
@Slf4j
public class AccountsServiceImpl implements IAccountsService {

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if (optionalCustomer.isPresent()) {
            throw new ResourceExistsException("Customer", "mobileNumber "+ customerDto.getMobileNumber());
        }

        Customer savedCustomer = customerRepository.save(customer);
        Accounts savedAccount = accountsRepository.save(createNewAccount(savedCustomer));
//        sendCommunication(savedAccount, savedCustomer);
    }

//    private void sendCommunication(Accounts account, Customer customer) {
//        AccountsMsgDto accountsMsgDto = new AccountsMsgDto(
//                account.getAccountNumber(),
//                customer.getName(),
//                customer.getEmail(),
//                customer.getMobileNumber()
//        );
//        log.info("Sending Communication request for the details: {}", accountsMsgDto);
//
//        var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
//
//        log.info("Is the Communication request successfully triggered ? : {}", result);
//    }

    /**
     * @param customer - Customer Object
     * @return the new account details
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountType.SAVING);
        newAccount.setBranchAddress("123 Main Street, New York");
        return newAccount;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return Accounts Details based on a given mobileNumber
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

    /**
     * @param mobileNumber - Input Mobile Number
     * @return boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    /**
     * @param accountNumber - Long
     * @return boolean indicating if the update of communication status is successful or not
     */
    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdated = false;
        if(accountNumber !=null ){
            Accounts accounts = accountsRepository.findById(accountNumber).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountNumber.toString())
            );
            accounts.setCommunicationSw(true);
            accountsRepository.save(accounts);
            isUpdated = true;
        }
        return  isUpdated;
    }
}
