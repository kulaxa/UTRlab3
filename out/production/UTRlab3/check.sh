#! /bin/bash
for dire in ../testovi/*
do
	#echo $dire
#ls $dire
difs="$(java SimPa < $dire/primjer.in | diff $dire/primjer.out -)"
#java lab2 < $dire/t.ul
		
	if [ "$difs" = "" ];
	then
		echo "$dire : [OK]"
	else
		
		echo "$dire : "
		echo $difs
	fi	

done

