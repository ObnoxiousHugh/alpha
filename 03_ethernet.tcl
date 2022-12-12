set ns [new Simulator]
set nf [open PB3.nam w]
$ns namtrace-all $nf
set nd [open PB3.tr w]
$ns trace-all $nd
$ns color 1 Blue
$ns color 2 Red
proc finish { } {
global ns nf nd
$ns flush-trace
close $nf
close $nd
exec nam PB3.nam &
exit 0
}
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]
set n5 [$ns node]
set n6 [$ns node]
set n7 [$ns node]
set n8 [$ns node]
$n7 shape box
$n7 color Blue
$n8 shape hexagon
$n8 color Red
$ns duplex-link $n1 $n0 2Mb 1ms DropTail
$ns duplex-link $n2 $n0 2Mb 1ms DropTail
$ns duplex-link $n0 $n3 1Mb 2ms DropTail
$ns make-lan "$n3 $n4 $n5 $n6 $n7 $n8" 512Kb 40ms LL Queue/DropTail Mac/802_3
$ns duplex-link-op $n1 $n0 orient right-down
$ns duplex-link-op $n2 $n0 orient right-up
$ns duplex-link-op $n0 $n3 orient right
$ns queue-limit $n0 $n3 20
set tcp1 [new Agent/TCP]
$ns attach-agent $n1 $tcp1
set sink1 [new Agent/TCPSink]
$ns attach-agent $n7 $sink1
$ns connect $tcp1 $sink1
$tcp1 set class_ 1
$tcp1 set packetSize_ 55
set ftp1 [new Application/FTP]
$ftp1 attach-agent $tcp1
set tfile [open cwnd.tr w]
$tcp1 attach $tfile
$tcp1 trace cwnd_
$ns at 0.5 "$ftp1 start"
$ns at 5.0 "$ftp1 stop"
$ns at 5.5 "finish"
$ns run


BEGIN {
}
{
if($6= ="cwnd_")
printf("%f\t%f\t\n",$1,$7);
}
END {
}


ns PB3.tcl
awk –f PB3.awk cwnd.tr > tcp1
xgraph -x "time" -y "convalue" tcp1
