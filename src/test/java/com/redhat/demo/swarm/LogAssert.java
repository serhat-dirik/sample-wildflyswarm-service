package com.redhat.demo.swarm;

import static org.fest.assertions.Assertions.assertThat;
/**
 * @author Bob McWhirter
 */
public class LogAssert {

    private final Log log;

    LogAssert(Log log) {
        this.log = log;
    }

    public LogAssert hasLineContaining(String match) {
        assertThat( this.log.getLines().stream().filter( e->e.contains(match)).count() ).describedAs( "Should contain '" + match + "'" ).isGreaterThan( 0);
        return this;
    }

}
