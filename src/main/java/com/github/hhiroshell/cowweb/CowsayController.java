package com.github.hhiroshell.cowweb;

import com.github.ricksbrown.cowsay.Cowsay;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Remote cowsay invoker using Spring Boot
 *
 * @author hhiroshell
 */
@RestController
@RequestMapping("/")
public class CowsayController {
    @Value("${moosage}")
    private String defaultMoosage;

    private static final String br = " ";

    private static final List<String> cowfiles;

    static {
        cowfiles = Arrays.asList(new String[]{"default"});
//        List<String> infelicities = Arrays.asList(new String[]{"head-in", "telebears", "sodomized"});
//        List<String> c = new ArrayList<>();
//        Arrays.stream(Cowsay.say(new String[]{"-l"}).split(br)).forEach(f -> {
//            if (!infelicities.contains(f)) {
//                c.add(f);
//            }
//        });
//        cowfiles = Collections.unmodifiableList(c);
    }

    /**
     * Return cowsay's 'say' message.
     *
     * @return Cowsay's 'say' message.
     */
    @RequestMapping(value = {"/", "/say"})
    public String say(@RequestParam(required = false) Optional<String> moosage) {
        Optional<String> env = moosage.map(s -> System.getenv(s));
        String msg = env.orElse(moosage.orElse(defaultMoosage));
        return Cowsay.say(new String[]{"-f", getRandomCowfile(), msg});
    }

    /**
     * Return cowsay's 'think' message.
     *
     * @return Cowsay's 'think' message.
     */
    @RequestMapping("/think")
    public String think(@RequestParam(required = false) Optional<String> moosage) {
        Optional<String> env = moosage.map(t -> System.getenv(t));
        String msg = env.orElse(moosage.orElse(defaultMoosage));
        return Cowsay.think(new String[]{"-f", getRandomCowfile(), msg});
    }

    private static String getRandomCowfile() {
        return cowfiles.get(new Random().nextInt(cowfiles.size()));
    }

    /**
     * Send back a fixed String.
     *
     * @return Send back a fixed String.
     */
    @RequestMapping("/ping")
    public String ping() {
        System.out.println("I'm working...");
        return "I'm working...";
    }

}
