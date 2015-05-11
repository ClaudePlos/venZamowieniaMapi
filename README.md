# venZamowieniaMapi
łącznie z oracle
-- add ks 2015-05-11
apka zupdatowana aby miec wpis jak loczyć si z baza:
!!! wazne 

Po dodaniu aplikacji do netbeansa dodaj Persistance Unit

1. persistance.xml

--<persistence version="2.1" xmlns="http://xmlns.jcp.org/xml/ns/persistence" --xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence --http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
--  <persistence-unit name="venZamowieniaMapiPU2" transaction-type="JTA">
--    <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
--    <jta-data-source>oracle_local</jta-data-source>
--    <exclude-unlisted-classes>false</exclude-unlisted-classes>
--    <properties/>
--  </persistence-unit>
--</persistence>

-- w pliku jest odwolanie do oracle_local, które tworzymy w glassfish w View Domain Admin Console

2. pozniej dodaj jakis model i  SessionBean For Entity Classes
plik facade 
!! Wazne - tak dodaj tworzenie EntityManager bo inaczej wywala nullPointer


@Stateless
public class OperatorVOFacade {
    
    
    private static final EntityManagerFactory emfInstance =
		        Persistence.createEntityManagerFactory("venZamowieniaMapiPU2");
    private EntityManager em;

   

    public OperatorVOFacade() {
        em = emfInstance.createEntityManager();
    }
    
    
    public List<OperatorVO> listaOperatorow()
    {
        
      List<OperatorVO> users = new ArrayList<OperatorVO>();
      
      List<Object> usersOb = em.createQuery("SELECT u FROM OperatorVO u").getResultList();
     //  List<Object> usersOb = em.createNativeQuery("SELECT id_operator, kod, haslo FROM operatorzy").getResultList(); 
       return users;
       
    }
    
}

