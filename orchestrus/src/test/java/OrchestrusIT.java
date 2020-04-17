import orchestrus.OrchestrusApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = OrchestrusApplication.class)
public class OrchestrusIT {

    /*
    Vérifier qu’il reçoive bien les connexions du client
    Vérifier qu’il se connecte bien avec les travailleurs
    Vérifier qu’il communique bien avec la BDD via l’interface
    Vérifier que la connexion soit bien enregistrée et validée à intervalle régulier
    Vérifier que les informations et l’état du travail soient bien conservés et soient corrects
    Vérifier que les informations transmises depuis le client soient identiques à celles reçues par le travailleur
    */

    @Test
    public void testIT() {
        assertEquals(0, 1);
    }
}
