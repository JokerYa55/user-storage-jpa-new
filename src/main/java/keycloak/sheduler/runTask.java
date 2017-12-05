/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keycloak.sheduler;

import java.util.logging.Logger;
import javax.ejb.Singleton;

/**
 *
 * @author vasil
 */
@Singleton
public class runTask {

    Logger log = Logger.getLogger(runTask.class.getName());
    long count = 0;
    //@Schedule(hour="*", minute="1")
    public void runsEveryMinute() {
        count ++;
        log.info(" runs EveryMinute " + count);
        //log.info(Util.doGet("http://sql.ru", null));
    }
}
