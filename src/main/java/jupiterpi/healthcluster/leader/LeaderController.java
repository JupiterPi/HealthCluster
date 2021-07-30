package jupiterpi.healthcluster.leader;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leader")
public class LeaderController {
    private Leader leader = null;

    private boolean noLeader() {
        return leader == null;
    }

    @PostMapping("/register/{clientUrl}")
    public String registerClient(@PathVariable String clientUrl) {
        if (noLeader()) return "not leader";
        // ...
        return "ok";
    }
}