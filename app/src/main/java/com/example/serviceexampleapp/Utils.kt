package com.example.serviceexampleapp

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

object Utils {

     fun scheduleJobService(context: Context?){
         if (context != null){
             val serviceComponent = ComponentName(context, BackgroundService::class.java)
             val builder = JobInfo.Builder(0, serviceComponent)
             builder.setMinimumLatency(5 * 1000.toLong())
             builder.setPersisted(true)
             builder.setOverrideDeadline(40 * 1000.toLong())

             val jobScheduler =  context.getSystemService(AppCompatActivity.JOB_SCHEDULER_SERVICE) as JobScheduler
             jobScheduler.schedule(builder.build())
         }

    }
}