set bwDL(gsm) 9600
set bwUL(gsm) 9600
set propDL(gsm) .500
set propUL(gsm) .500
set buf(gsm) 10
set ns [new Simulator]
set na [open Lab5.nam w]
$ns namtrace-all $na
set nt [open Lab5.tr w]
$ns trace-all $nt
set nodes(c1) [$ns node]
set nodes(ms) [$ns node]
set nodes(bs1) [$ns node]
set nodes(bs2) [$ns node]
set nodes(c2) [$ns node]
proc cell_topo {} {
global ns nodes
$ns duplex-link $nodes(c1) $nodes(bs1) 3Mbps 10ms DropTail
$ns duplex-link $nodes(bs1) $nodes(ms) 1 1 RED
$ns duplex-link $nodes(ms) $nodes(bs2) 1 1 RED
$ns duplex-link $nodes(bs2) $nodes(c2) 3Mbps 50ms DropTail
}
switch gsm {
gsm -
gprs -
umts {cell_topo}
}
$ns bandwidth $nodes(bs1) $nodes(ms) $bwDL(gsm) simplex
$ns bandwidth $nodes(ms) $nodes(bs1) $bwUL(gsm) simplex
$ns bandwidth $nodes(bs2) $nodes(ms) $bwDL(gsm) simplex
$ns bandwidth $nodes(ms) $nodes(bs2) $bwUL(gsm) simplex
$ns delay $nodes(bs1) $nodes(ms) $propDL(gsm) simplex
$ns delay $nodes(ms) $nodes(bs1) $propDL(gsm) simplex
$ns delay $nodes(bs2) $nodes(ms) $propDL(gsm) simplex
$ns delay $nodes(ms) $nodes(bs2) $propDL(gsm) simplex
$ns queue-limit $nodes(bs1) $nodes(ms) $buf(gsm)
$ns queue-limit $nodes(ms) $nodes(bs1) $buf(gsm)
$ns queue-limit $nodes(bs2) $nodes(ms) $buf(gsm)
$ns queue-limit $nodes(ms) $nodes(bs2) $buf(gsm)
$ns insert-delayer $nodes(ms) $nodes(bs1) [new Delayer]
$ns insert-delayer $nodes(bs1) $nodes(ms) [new Delayer]
$ns insert-delayer $nodes(ms) $nodes(bs2) [new Delayer]
$ns insert-delayer $nodes(bs2) $nodes(ms) [new Delayer]
set tcp [new Agent/TCP]
$ns attach-agent $nodes(c1) $tcp
set sink [new Agent/TCPSink]
$ns attach-agent $nodes(c2) $sink
set ftp [new Application/FTP]
$ftp attach-agent $tcp
$ns connect $tcp $sink
proc End {} {
global ns nt
$ns flush-trace
close $nt
exec nam Lab5.nam &amp;
exec awk -f lab5.awk Lab5.tr &amp;
exec xgraph -P -bar -x TIME -y DATA gsm.xg &amp;
exit 0
}
$ns at 0.0 &quot;$ftp start&quot;
$ns at 10.0 &quot;End&quot;
$ns run


AWK File:
BEGIN {Total_no_of_pkts=0;}
{
if($1 == &quot;r&quot;)
{
Total_no_of_pkts = Total_no_of_pkts + $6;
printf(&quot;%f %d\n&quot;,$2,Total_no_of_pkts) &gt;&gt; &quot;gsm.xg&quot;
}
}
END{}