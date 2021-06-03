import extensions.OnOffQN;
import extensions.ServerOnOff;
import extensions.SinkLifetime;

import network.*;
import tools.*;


class OnOffSimulacion extends Sim {

    public static double duration = 0;
    public static Exp serviceTime;


    // Example termination function
    public boolean stop() {
        return now() > duration;
    }


    // Here, the constructor starts the simulation.
    public OnOffSimulacion(double d) {

        duration = d;

        Network.initialise();
        serviceTime = new Exp(8);

        Exp onlineTime = new Exp(0.05);

        Exp offlineTime = new Exp(0.05);

        Delay serveTime = new Delay(serviceTime);
        Source source = new Source("Source", new Exp(3.5));

        OnOffQN mm1 = new OnOffQN("MM1", serveTime, 1, "Exp", 2);

        ServerOnOff serverOnOff = new ServerOnOff(onlineTime, offlineTime, duration, mm1);

        SinkLifetime sink = new SinkLifetime("Sink");

        source.setLink(new Link(mm1));
        mm1.setLink(new Link(sink));

        simulate();


        Network.logResults();

    }
    public static void main(String args[]) {
        new OnOffSimulacion(10000);
        Network.displayResults( 0.01 ) ;
    }
}
