package spring.dao;

import com.ibatis.common.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

@Component
public class InitDB {

    @Autowired
    public InitDB() throws SQLException, IOException {
        Resource resource = new ClassPathResource("script.sql");
        File script = resource.getFile();
        ScriptRunner runner = new ScriptRunner(JDBC.getInstance().getConnection(), false,false);
        runner.runScript(new FileReader(script));
    }
}
