/*************************************************************************************
 * Copyright (C) 2016 GENERAL BYTES s.r.o. All rights reserved.
 *
 * This software may be distributed and modified under the terms of the GNU
 * General Public License version 2 (GPL2) as published by the Free Software
 * Foundation and appearing in the file GPL2.TXT included in the packaging of
 * this file. Please note that GPL2 Section 2[b] requires that all works based
 * on this software must also be made publicly available under the terms of
 * the GPL2 ("Copyleft").
 *
 * Contact information
 * -------------------
 *
 * GENERAL BYTES s.r.o.
 * Web      :  http://www.generalbytes.com
 *
 ************************************************************************************/

package com.generalbytes.bitrafael.api.examples;

import com.generalbytes.bitrafael.api.client.Client;
import com.generalbytes.bitrafael.api.client.IClient;
import com.generalbytes.bitrafael.api.dto.AddressInfo;
import com.generalbytes.bitrafael.api.dto.TxInfo;
import com.generalbytes.bitrafael.api.transaction.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class ClientExample {
    public static void main(String[] args) {
        IClient c = new Client("https://coin.cz");

//        final TxFeesInfo fees = c.getRecommendedTransactionFeesPerByte();
//        System.out.println("Recommended transaction fees per byte: " + fees);

        final IClient.RiskLevel transactionRiskLevel = c.getTransactionRiskLevel("3047dff08cd6e2dce2febfc7592bedd46d4dcb400e654b700e0c64b7178cbd3f");
        System.out.println("transactionRiskLevel = " + transactionRiskLevel);

        System.exit(0);
        //test currency conversion related functions
        BigDecimal amount = c.convertAmount(BigDecimal.ONE, "USD", "BTC");
        System.out.println("1 USD = " + amount + " BTC");
        amount = c.convertAmount(BigDecimal.ONE, "BTC", "USD");
        System.out.println("1 BTC = " + amount + " USD");

        amount = c.convertAmount(BigDecimal.ONE, "mBTC", "BTC");
        System.out.println("1 mBTC = " + amount + " BTC");
        amount = c.convertAmount(BigDecimal.ONE, "BTC", "mBTC");
        System.out.println("1 BTC = " + amount + " mBTC");

        BigDecimal b1 = c.getAddressBalance("1tEsTvxKTYsejxMQmAEMMNXB5M5JWTXAN");
        BigDecimal b2 = c.getAddressBalanceConfirmed("1tEsTvxKTYsejxMQmAEMMNXB5M5JWTXAN");
        System.out.println("balances = " + b1 + " " + b2);

        final AddressInfo addressInfo = c.getAddressInfo("1rAfaELDCv1fKghK6vSsJXf8Q5GvU2Eqn", Integer.MAX_VALUE);
        System.out.println("addressInfo = " + addressInfo);

        final List<Transaction> transactions = Transaction.buildTransactions(addressInfo.getTxInfos(), "1rAfaELDCv1fKghK6vSsJXf8Q5GvU2Eqn");
        System.out.println("Transactions of 1rAfaELDCv1fKghK6vSsJXf8Q5GvU2Eqn");
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            System.out.println(" " + i +". " + transaction);
        }



        //following line will always cause error as the private key is not set
        String txHash = c.send("5JFL....private_key_for_1tEsTvxKTYsejxMQmAEMMNXB5M5JWTXAN", new BigDecimal("0.0002"), "34ZzYw5xB8JTFcECJrFo12sCEGK9St11bU");
        System.out.println("txHash = " + txHash);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (txHash== null) {
            txHash = "edcfc87234dd501f244791e1b112210914a36d6f3d820e7d122e5db1903001c0";
        }
        final long transactionConfirmations = c.getTransactionConfirmations(txHash);
        System.out.println("transactionConfirmations = " + transactionConfirmations);
        final long transactionHeight = c.getTransactionHeight(txHash);
        System.out.println("transactionHeight = " + transactionHeight);
        final long currentBlockchainHeight = c.getCurrentBlockchainHeight();
        System.out.println("currentBlockchainHeight = " + currentBlockchainHeight);
        final TxInfo txinfo = c.getAddressLastTransactionInfo("17oM8y8YEARHHpi6TmoXjLEcF5VMmdGqrR");
        System.out.println("txinfo = " + txinfo);


    }
}
