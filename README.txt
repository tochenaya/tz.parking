Автоматизация работы парковки. 10 мест.
Действия : машина (номер, марка) зашла на стоянку, ушла со стоянки.
Подсчет выручки за день из расчета 100 р в час с округлением в большую сторону
Возможность получить список машин на стоянке в данный момент.
Наличие машины на стоянке.
Кол-во свободных  мест
взаимодействие через REST
EJB
WILDFLY
HIBERNATE


<datasource jta="true" jndi-name="java:jboss/parking-datasource" pool-name="parking-datasource" enabled="true" use-java-context="true" spy="false" use-ccm="true">
    <connection-url>jdbc:mysql://localhost:3306/parking?autoReconnect=true&amp;characterEncoding=UTF-8</connection-url>
    <driver-class>com.mysql.jdbc.Driver</driver-class>
    <driver>mysql</driver>
    <transaction-isolation>TRANSACTION_READ_COMMITTED</transaction-isolation>
    <pool>
        <min-pool-size>0</min-pool-size>
        <initial-pool-size>0</initial-pool-size>
        <max-pool-size>10</max-pool-size>
        <prefill>false</prefill>
        <use-strict-min>false</use-strict-min>
        <flush-strategy>FailingConnectionOnly</flush-strategy>
    </pool>
    <security>
        <user-name>root</user-name>
        <password>root</password>
    </security>
    <timeout>
        <idle-timeout-minutes>5</idle-timeout-minutes>
    </timeout>
    <statement>
        <track-statements>true</track-statements>
        <prepared-statement-cache-size>500</prepared-statement-cache-size>
    </statement>
</datasource>